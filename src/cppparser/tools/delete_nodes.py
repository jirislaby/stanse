def delete_nodes(fin, fnname, nodes):
    import json
    src = json.load(fin)

    nodes = set(nodes)
    fn = src[fnname]

    done = False
    while not done:
        done = True
        for i, node in enumerate(fn['nodes']):
            for op in node[2]:
                if op[0] == 'node' and op[1] in nodes and i not in nodes:
                    done = False
                    nodes.add(i)

    node_mapping = {}
    for i in xrange(len(fn['nodes'])):
        if i not in nodes:
            node_mapping[i] = len(node_mapping)

    for i in sorted(nodes, reverse=True):
        del fn['nodes'][i]

    for node in fn['nodes']:
        new_succs = []
        for succ in node[1]:
            if succ[0] in node_mapping:
                new_succs.append((node_mapping[succ[0]], succ[1], succ[2]))
        node[1] = new_succs
        for op in node[2]:
            if op[0] == 'node':
                op[1] = node_mapping[op[1]]

    fn['entry'] = node_mapping[fn['entry']]

    return json.dumps(src, indent=3)


if __name__ == '__main__':
    import argparse
    parser = argparse.ArgumentParser(description='Remove the given set of nodes.')
    parser.add_argument('-i', '--infile', default='-', help='The JSON-encoded CFG forest to be pruned.')
    parser.add_argument('-o', '--outfile', help='The file to store the pruned CFG to.')
    parser.add_argument('fnname', help='The name of the CFG to prune.')
    parser.add_argument('node', nargs='*', help='The name of the CFG to prune.')
    args = parser.parse_args()

    import sys
    fin = open(args.infile, 'r') if args.infile != '-' else sys.stdin
    res = delete_nodes(fin, args.fnname, map(int, args.node))
    fin.close()

    fout = open(args.outfile, 'w') if args.outfile else open(args.infile, 'w') if args.infile != '-' else sys.stdout
    fout.write(res)
    fout.close()
