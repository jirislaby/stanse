def _is_subgraph_isomorphism(tg, g, isom, sym_isom):
    for i, tv in enumerate(tg['nodes']):
        v = g['nodes'][isom[i]]

        if tv[0] != v[0] or len(tv[1]) > len(v[1]) or len(tv[2]) != len(v[2]):
            return False
            
        if tv[0] == 'phi':
            # The order of operands doesn't matter
            if any(op[0] != 'node' for op in v[2]) or any(top[0] != 'node' for top in tv[2]):
                # TODO: report error
                return False

            vs = set(op[1] for op in v[2])
            if any(isom[top[1]] not in vs for top in tv[2]):
                return False;
        else:
            for top, op in zip(tv[2], v[2]):
                if top[0] != op[0]:
                    return False

                if top[0] == "var" or top[0] == "varptr":
                    if sym_isom[top[1]] != op[1]:
                        return False
                elif top[0] == "node":
                    if isom[top[1]] != op[1]:
                        return False
                elif top[1] != op[1]:
                    return False

        for tsucc, tidx, tcond in tv[1]:
            for succ, idx, cond in v[1]:
                if succ == isom[tsucc] and tidx == idx and tcond == cond:
                    break
            else:
                return False

    return True

def _vertices_topo_match(tv, v):
    if tv[0] != v[0] or len(tv[1]) > len(v[1]) or len(tv[2]) != len(v[2]):
        return False
        
    for top, op in zip(tv[2], v[2]):
        if top[0] != op[0]:
            return False

    return True

def _update_sym_isom(si, tn, n, tl, l):
    for top, op in zip(tn[2], n[2]):
        if top[0] != op[0]:
            return False
        if op[0] == 'var' or op[0] == 'varptr':
            if top[1] not in si:
                if (top[1] in tl) == (op[1] in l):
                    si[top[1]] = op[1]
                else:
                    return False
            if si[top[1]] != op[1]:
                return False

    return True

def _is_subgraph(tg, tu, g, u, isom, sym_isom):
    if not tu:
        return (isom, sym_isom) if _is_subgraph_isomorphism(tg, g, isom, sym_isom) else None

    tv = tu[-1]
    for v in u:
        si = dict(sym_isom)
        if not _update_sym_isom(si, tg['nodes'][tv], g['nodes'][v], tg['locals'], g['locals']):
            continue

        if not _vertices_topo_match(tg['nodes'][tv], g['nodes'][v]):
            continue
    
        isom[tv] = v
        if _is_subgraph(tg, [x for x in tu if x != tv], g, [x for x in u if x != v], isom, si):
            return isom, si

    if tv in isom:
        del isom[tv]

def _check_fns(tfn, fn):
    tu = range(len(tfn["nodes"]))
    u = range(len(fn["nodes"]))

    if 'params' in tfn and len(tfn['params']) != len(fn['params']):
        return None

    sym_isom = dict(zip(tfn['params'], fn['params'])) if 'params' in tfn else {}
    isom = { tfn["entry"]: fn["entry"] }
    tu = [x for x in tu if x != tfn["entry"]]
    u = [x for x in u if x != fn["entry"]]
    if _update_sym_isom(sym_isom, tfn['nodes'][tfn["entry"]], fn['nodes'][fn['entry']], tfn['locals'], fn['locals']):
        return _is_subgraph(tfn, tu, fn, u, isom, sym_isom)

def main(fin, ftempl, err, showMatches=False):
    import json
    templ = json.load(ftempl)
    src = json.load(fin)
    
    for fnname, templfn in templ.iteritems():
        if fnname not in src:
            err.write('Function %s expected but not seen.' % fnname)
            continue
            
        fn = src[fnname]
        isom = _check_fns(templfn, fn)
        if not isom:
            err.write('error: %s: function does not match.' % fnname)
            err.write('templ: %s' % str(templfn))
            err.write('input: %s' % str(fn))
        elif showMatches:
            err.write('note: %s: functions match, isom = %s.' % (fnname, str(isom)))
            err.write('templ: %s' % str(templfn))
            err.write('input: %s' % str(fn))

class _ErrorReporter:
    def __init__(self, fout, fname):
        self.fout = fout
        self.fname = fname

    def write(self, str):
        self.fout.write('%s: %s\n' % (self.fname, str))

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
    main(fin, ftemplate, _ErrorReporter(sys.stdout, args.template), args.showMatches)
