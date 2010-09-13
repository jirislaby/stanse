def tests2pdf(parser, paths, target_dir):
    from cfg2pdf import cfg2pdf
    from subprocess import Popen, PIPE
    for templ in paths:
        source = os.path.splitext(templ)[0] + '.cpp'
        ftempl = open(templ, 'r')

        templpdf = templ + '.pdf'
        if target_dir:
            templpdf = os.path.join(target_dir, os.path.split(templ)[1] + '.pdf')

        cfg2pdf(ftempl, open(templpdf, 'w'))
        try:
            try:
                p = Popen([parser, '-j', source], stdout=PIPE)
                sourcepdf = source + '.pdf'
                if target_dir:
                    sourcepdf = os.path.join(target_dir, os.path.split(source)[1] + '.pdf')
                cfg2pdf(p.stdout, open(sourcepdf, 'w'))
                p.wait()
            except OSError, e:
                print 'Cannot execute the parser:', e.strerror
                break
        finally:
            ftempl.close()

if __name__ == '__main__':
    import argparse
    args = argparse.ArgumentParser(description='Turn a test case to a pair of pdfs.')
    args.add_argument('template', nargs='*', help='A path to the .cfg file identifyin the test.');
    args.add_argument('-p', '--parser', help='A path to the cppparser executable.');
    args.add_argument('-t', '--target', help='The target directory.');
    args = args.parse_args()

    import os.path
    parser = args.parser if args.parser else os.path.join(os.path.split(__file__)[0], '..', 'Debug', 'cppparser')

    templs = args.template if args.template else ['*.cfg']
    templs = [os.path.join(arg, '*.cfg') if os.path.isdir(arg) else arg for arg in templs]

    import glob
    templs = [item for arg in templs for item in glob.glob(arg)]

    tests2pdf(parser, templs, args.target)
