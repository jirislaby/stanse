if __name__ == '__main__':
    import argparse
    args = argparse.ArgumentParser(description='Run a test or a sequence of tests of cppparser.')
    args.add_argument('template', nargs='*', help='The JSON-encoded CFG forest to be checked.');
    args.add_argument('-p', '--parser', help='A path to the cppparser executable.');
    args = args.parse_args()

    import os.path
    parser = args.parser if args.parser else os.path.join(os.path.split(__file__)[0], '..', 'Debug', 'cppparser')

    templs = args.template if args.template else ['*.cfg']
    templs = [os.path.join(arg, '*.cfg') if os.path.isdir(arg) else arg for arg in templs]

    import glob
    templs = [item for arg in templs for item in glob.glob(arg)]

    import sys
    from subprocess import Popen, PIPE
    from match_cfgs import match_cfgs, ErrorReporter
    for templ in templs:
        source = os.path.splitext(templ)[0] + '.cpp'
        print os.path.split(templ)[1]
        ftempl = open(templ, 'r')
        try:
            try:
                p = Popen([parser, '-j', '-w', source], stdout=PIPE)
            except OSError, e:
                print 'Cannot execute the parser:', e.strerror
                break
            match_cfgs(p.stdout, ftempl, ErrorReporter(sys.stdout, os.path.abspath(source), os.path.abspath(templ)))
        finally:
            ftempl.close()
