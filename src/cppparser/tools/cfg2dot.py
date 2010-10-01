from xml.sax.saxutils import escape as xml_escape

def cfg2dot(fin, fout):
    import json
    src = json.load(fin)['cfgs']

    fout.write('digraph G {\n')
    fout.write('    node [shape=record];\n')
    fnidx = 0
    for fnname, fn in src.iteritems():
        skipexit2 = True
        for node in fn['nodes']:
            for succ in node[1]:
                if succ[1] != 2 and fn['nodes'][succ[0]][0] == 'exit' and fn['nodes'][succ[0]][2][0][1] == '2':
                    skipexit2 = False
                    break
            if not skipexit2:
                break

        fout.write('    entry%d [label="%s"];\n' % (fnidx, xml_escape(fnname)))
        fout.write('    entry%d -> node%d_%d;\n' % (fnidx, fnidx, fn['entry']))
        for i, node in enumerate(fn['nodes']):
            if skipexit2 and node[0] == 'exit' and node[2][0][1] == '2':
                continue

            if node[0] == 'call':
                ops = node[2][1:]
                opstr = node[2][0][1] + '('
            elif node[0] == 'value':
                ops = node[2]
                opstr = ''
            else:
                ops = node[2]
                opstr = node[0] + '['
            args = []
            for op in ops:
                if op[0] == 'varptr':
                    args.append('&' + str(op[1]))
                elif op[0] != 'node':
                    args.append(str(op[1]))
                else:
                    args.append('$' + str(op[1]))
            opstr = opstr + ', '.join(args)
            if node[0] == 'call':
                opstr = opstr + ')'
            elif node[0] != 'value':
                opstr = opstr + ']'
            fout.write('    node%d_%d [label="<f1> %d |<f2> %s"];\n' % (fnidx, i, i, xml_escape(opstr)));
            for succ in node[1]:
                if succ[1] == 0:
                    fout.write('    node%d_%d -> node%d_%d [label="%s"];\n' % (fnidx, i, fnidx, succ[0], xml_escape(succ[2])))
                if succ[1] == 1:
                    fout.write('    node%d_%d -> node%d_%d [label="%s",style="dotted"];\n' % (fnidx, i, fnidx, succ[0], xml_escape(succ[2])))
        fnidx += 1
    fout.write('}')

if __name__ == '__main__':
    import argparse
    parser = argparse.ArgumentParser(description='Convert a json-encoded CFG to a dot file.')
    parser.add_argument('infile', nargs='?', default='-', help='The JSON-encoded CFG forest to be checked.');
    args = parser.parse_args()
    
    import sys
    fin = open(args.infile, 'r') if args.infile != '-' else sys.stdin
    cfg2dot(fin, sys.stdout)
