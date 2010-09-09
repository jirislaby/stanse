def main(fin, fout):
    import json
    src = json.load(fin)
    
    fout.write('digraph G {\n')
    fout.write('    node [shape=record];\n')
    fnidx = 0
    for fnname, fn in src.iteritems():
        fout.write('    entry%d [label="%s"];\n' % (fnidx, fnname))
        fout.write('    entry%d -> node%d_%d;\n' % (fnidx, fnidx, fn['entry']))
        for i, node in enumerate(fn['nodes']):
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
            fout.write('    node%d_%d [label="<f1> %d |<f2> %s"];\n' % (fnidx, i, i, opstr));
            for succ in node[1]:
                fout.write('    node%d_%d -> node%d_%d [label="%s"];\n' % (fnidx, i, fnidx, succ[0], succ[2]))
        fnidx += 1
    fout.write('}')

if __name__ == '__main__':
    import argparse
    parser = argparse.ArgumentParser(description='Convert a json-encoded CFG to a dot file.')
    parser.add_argument('infile', nargs='?', default='-', help='The JSON-encoded CFG forest to be checked.');
    args = parser.parse_args()
    
    import sys
    fin = open(args.infile, 'r') if args.infile != '-' else sys.stdin
    main(fin, sys.stdout)
