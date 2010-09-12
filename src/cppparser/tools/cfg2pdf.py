def cfg2pdf(fin, fout):
    from cfg2dot import cfg2dot
    from subprocess import Popen, PIPE
    
    p = Popen(['dot', '-Tpdf'], stdin=PIPE, stdout=fout)
    cfg2dot(fin, p.stdin)
    p.stdin.close()

if __name__ == '__main__':
    import argparse
    args = argparse.ArgumentParser(description='Convert a json-encoded CFG to a pdf file (uses dot).')
    args.add_argument('infile', nargs='?', default='-', help='The JSON-encoded CFG forest to be checked.');
    args.add_argument('outfile', nargs='?', help='The JSON-encoded CFG forest to be checked.');
    args = args.parse_args()

    import os.path
    if not args.outfile:
        outfile = os.path.splitext(os.path.split(args.infile)[1])[0] + '.pdf' if args.infile != '-' else '-'
    else:
        outfile = args.outfile

    import sys
    fin = open(args.infile, 'r') if args.infile != '-' else sys.stdin
    fout = open(outfile, 'w') if outfile != '-' else sys.stdout
    cfg2pdf(fin, fout)
