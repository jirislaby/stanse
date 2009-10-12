package cz.muni.stanse.configuration.source_enumeration;

import cz.muni.stanse.Stanse;
import cz.muni.stanse.utils.Make;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Map;

public final class MakefileSourceEnumerator extends
	ReferencedSourceCodeFileEnumerator {
    // public section

    public MakefileSourceEnumerator(final String makeFile,
                                    final String arguments) {
        super(makeFile);
        this.arguments = arguments;
    }

    @Override
    public synchronized List<String> getSourceCodeFiles()
	    throws SourceCodeFilesException {
	if (files == null) {
	    final File batchFile = createBatchFile(getReferenceFile(),
						 getArguments());
	    files = new BatchFileEnumerator(batchFile.getAbsolutePath()).
		    getSourceCodeFiles();
          if (!batchFile.delete())
		System.err.println("Can't delete " +
			batchFile.getAbsolutePath());
	}
	return files;
    }

    public String getArguments() {
        return arguments;
    }

    // private section

    private static File createBatchFile(final String makeFile,
                                          final String arguments)
                                          throws SourceCodeFilesException {
	final File makefile = new File(makeFile);
	if (!makefile.exists())
	    throw new SourceCodeFilesException("Makefile '" +
		    makefile.getAbsolutePath() + "' doesn't exist");
        final File batchFile;
	try {
	    batchFile = File.createTempFile("stanse_jobfile", null);
	} catch (IOException e) {
	    System.err.println("Can't create a temp jobfile");
	    throw new SourceCodeFilesException(e);
	}
	ProcessBuilder builder = new ProcessBuilder(createMakeCmdLine(
		makefile, arguments));
        final Map<String, String> environment = builder.environment();
        environment.put("JOB_FILE", batchFile.getAbsolutePath());
        environment.put("PATH", environment.get("PATH") + File.pathSeparator +
                        Stanse.getInstance().getRootDirectory() +
                        File.separator + "bin");
	try {
	    File dir = makefile.getParentFile();
	    if (dir == null)
		dir = new File(".");
	    builder.directory(dir);
	    environment.put("PWD", dir.getAbsolutePath());
	    Process p = builder.start();
	    p.waitFor();
	    /*char b[] = new char[1600];
	    InputStreamReader isr = new InputStreamReader(p.getErrorStream());
	    OutputStreamWriter osw = new OutputStreamWriter(System.err);
	    int len = isr.read(b);
	    System.err.println("======err:");
	    if (len > 0)
		osw.write(b, 0, len);
	    osw.flush();
	    System.err.println("======out:");
	    isr = new InputStreamReader(p.getInputStream());
	    len = isr.read(b);
	    if (len > 0)
		osw.write(b, 0, len);
	    osw.flush();
	    System.err.println("======");*/
	} catch (IOException e) {
	    throw new SourceCodeFilesException(e);
	} catch (InterruptedException e) {
	    throw new SourceCodeFilesException(e);
	}
        return batchFile;
    }

    private static List<String> createMakeCmdLine(final File makefile,
	    final String args) {
        final List<String> result = Make.<String>linkedList("make", "CC=stcc",
		"-f" + makefile.getName());
        if (!args.isEmpty())
            result.addAll(java.util.Arrays.asList(args.split("[ \t]")));
        return result;
    }

    private final String arguments;
    private List<String> files = null;
}
