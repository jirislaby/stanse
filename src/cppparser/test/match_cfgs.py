class GraphsNotIsomorphicError(RuntimeError):
    pass

def _refine(s1, s2, mapping):
    assert len(s1) <= len(s2)
    changed = []
    new_mapping = []
    for i, (q1, q2) in enumerate(mapping):
        if q1 <= s1:
            if q2 <= s2:
                continue
            q2.intersection_update(s2)
            if len(q1) > len(q2):
                raise GraphsNotIsomorphicError()
            changed.append(i)
        else:
            i1 = q1.intersection(s1)
            i2 = q2.intersection(s2)
            q1 -= s1
            q2 -= s2

            if len(q1) > len(q2) or len(i1) > len(i2):
                raise GraphsNotIsomorphicError()

            if i1:
                new_mapping.append((i1, i2))
                changed.append(i)

    changed.extend(xrange(len(mapping), len(mapping)+len(new_mapping)))
    mapping.extend(new_mapping)
    return changed

def _topo_refine(g1, g2, start, mapping):
    q = list(start)
    while q:
        # TODO: q should be a set and we should choose the smallest partition
        i = q.pop(0)
        s1 = {}
        for u in mapping[i][0]:
            for e in g1.out_edges(u):
                ed = g1[e]
                if not ed in s1:
                    s1[ed] = set()
                s1[ed].add(g1.target(e))
        s2 = {}
        for u in mapping[i][1]:
            for e in g2.out_edges(u):
                ed = g2[e]
                if not ed in s2:
                    s2[ed] = set()
                s2[ed].add(g2.target(e))

        for e, ts in s1.iteritems():
            if not e in s2:
                raise GraphsNotIsomorphicError()
            q.extend(_refine(ts, s2[e], mapping))

def _struct_refine(g1, g2):
    s1 = g1.vertices()
    s2 = g2.vertices()

    source_classes = []
    for u in s1:
        for sc in source_classes:
            if g1[u] == g1[iter(sc).next()]:
                sc.add(u)
                break
        else:
            source_classes.append(set([u]))

    dest_classes = [set() for x in source_classes]
    for u in s2:
        for sc, dc in zip(source_classes, dest_classes):
            if g1[iter(sc).next()] == g2[u]:
                dc.add(u)
                break

    for dc in dest_classes:
        if not dc:
            raise GraphsNotIsomorphicError()

    return list(zip(source_classes, dest_classes))

def _prepare_mapping(g1, g2):
    mapping = _struct_refine(g1, g2)
    _refine(set([g1.entry()]), set([g2.entry()]), mapping)
    _topo_refine(g1, g2, range(len(mapping)), mapping)
    return mapping

def _verify_edges(g1, g2, isom):
    for s, d in isom.iteritems():
        se = set((g1[x], isom[g1.target(x)]) for x in g1.out_edges(s))
        de = set((g2[x], g2.target(x)) for x in g2.out_edges(d))
        if not (se <= de):
            return False
    return True

def _find_isomorphism(g1, g2, mapping, isom=None):
    if isom is None:
        isom = {}

    if not mapping:
        return isom if _verify_edges(g1, g2, isom) else None

    def _t(slist, dset, isom):
        assert len(slist) <= len(dset)

        if not slist:
            return _find_isomorphism(g1, g2, mapping[1:], isom)

        for d in dset:
            isom[slist[0]] = d
            res = _t(slist[1:], dset - set([d]), isom)
            if res is not None:
                return res
        del isom[slist[0]]

    return _t(list(mapping[0][0]), mapping[0][1], isom)

class Cfg:
    def __init__(self, g):
        self.g = g
        self.parammap = { p: i for i, p in enumerate(g['params']) }
        self.localset = set(self.g['locals'])

        self.nodes = []
        for node in g['nodes']:
            phi = node[0] == 'phi'
            new_succs = [(x[0], (x[1], x[2])) for x in node[1]]
            new_ops = []
            for i, (optype, opid) in enumerate(node[2]):
                if optype == 'node':
                    new_succs.append((opid, None if phi else i))
                    new_ops.append((optype, None))
                elif optype in ('var', 'varptr'):
                    if opid in self.parammap:
                        new_ops.append((optype, self.parammap[opid]))
                    elif opid in self.localset:
                        new_succs.append((opid, i))
                        new_ops.append((optype, None))
                    else:
                        new_ops.append((optype, opid))
                else:
                    new_ops.append((optype, opid))
            self.nodes.append((node[0], new_succs, new_ops))

    def entry(self):
        return self.g['entry']

    def vertices(self):
        for i in xrange(len(self.nodes)):
            yield i
        for v in self.g['locals']:
            yield v

    def out_edges(self, u):
        if isinstance(u, int):
            for succ, data in self.nodes[u][1]:
                yield u, data, succ

    def source(self, e):
        return e[0]

    def target(self, e):
        return e[2]

    def __getitem__(self, key):
        if isinstance(key, tuple):
            return key[1]
        if isinstance(key, str):
            return None
        return self.nodes[key][0], self.nodes[key][2]

def match_cfgs(fin, ftempl, err, showMatches=False):
    import json
    templ = json.load(ftempl)
    src = json.load(fin)
    
    for fnname, templfn in templ['cfgs'].iteritems():
        if fnname not in src['cfgs']:
            err.write_templ('error: function %s expected but not seen.' % fnname)
            continue

        fn = src['cfgs'][fnname]
        cfg = Cfg(fn)
        tcfg = Cfg(templfn)
        
        try:
            mapping = _prepare_mapping(tcfg, cfg)
            isom = _find_isomorphism(tcfg, cfg, mapping)
        except GraphsNotIsomorphicError:
            isom = None

        if not isom:
            err.write_templ('error: %s: function does not match.' % fnname)
            err.write_source(str(fn))
        elif showMatches:
            err.write_templ('note: %s: functions match, isom = %s.' % (fnname, str(isom)))
            err.write_source(str(fn))

class ErrorReporter:
    def __init__(self, fout, fname, tname):
        self.fout = fout
        self.fname = fname
        self.tname = tname

    def write_source(self, str):
        self.fout.write('%s: %s\n' % (self.fname, str))

    def write_templ(self, str):
        self.fout.write('%s: %s\n' % (self.tname, str))

if __name__ == '__main__':
    import argparse
    parser = argparse.ArgumentParser(description='Verify that the input contains all the behaviors specified in the template.')
    parser.add_argument('infile', nargs='?', default='-', help='The JSON-encoded CFG forest to be checked.');
    parser.add_argument('template', help='The JSON-encoded template the input is to be checked against.');
    parser.add_argument('-m', '--matches', dest='showMatches', action='store_const', const=True, default=False, help='Also show successful matches');
    args = parser.parse_args()
    
    import sys
    fin = open(args.infile, 'r') if args.infile != '-' else sys.stdin
    ftemplate = open(args.template, 'r')

    match_cfgs(fin, ftemplate, ErrorReporter(sys.stdout, args.infile, args.template), args.showMatches)
