package cz.muni.stanse;

import cz.muni.stanse.utils.Make;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Map;

public final class MakefileSourceEnumerator extends
                                           ReferencedSourceCodeFilesEnumerator {
    // public section

    public MakefileSourceEnumerator(final String makeFile,
                                    final String arguments) {
        super(makeFile);
        this.arguments = arguments;
    }

    @Override
    public List<String> getSourceCodeFiles() throws SourceCodeFilesException {
        List<String> result;
        synchronized(getClass()) {
            final String batchFile = createBatchFile(getReferenceFile(),
                                                     getArguments());
            result = new BatchFileEnumerator(batchFile).getSourceCodeFiles();
            new File(batchFile).delete();
        }
        return result;
    }

    public String getArguments() {
        return arguments;
    }

    // private section

    private static String createBatchFile(final String makeFile,
                                          final String arguments)
                                          throws SourceCodeFilesException {
        final String batchFile = "/tmp/tmp_stanse_batch_file_for_makefile.txt";
        final ProcessBuilder builder = new ProcessBuilder(createMakeCmdLine(
                                                                    arguments));
        final Map<String, String> environment = builder.environment();
        environment.put("JOB_FILE",batchFile);
        environment.put("PATH", environment.get("PATH") + File.pathSeparator +
                            Stanse.getRootDirectory() + File.separator + "bin");
	try {
	    builder.directory(new File(makeFile).getParentFile()).start().
		waitFor();
	} catch (IOException e) {
	    throw new SourceCodeFilesException(e);
	} catch (InterruptedException e) {
	    throw new SourceCodeFilesException(e);
	}
        return batchFile;
    }

    private static List<String> createMakeCmdLine(final String args) {
        final List<String> result = Make.<String>linkedList("make" ,"CC=stcc");
        if (!args.isEmpty())
            result.addAll(java.util.Arrays.asList(args.split("[ \t]")));
        return result;
    }

    private final String arguments;
}
