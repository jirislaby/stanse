package cz.muni.stanse;

public final class MakefileSourceEnumerator extends
                                           ReferencedSourceCodeFilesEnumerator {
    // public section

    public MakefileSourceEnumerator(final String makeFile,
                                    final String arguments) {
        super(makeFile);
        this.arguments = arguments;
    }

    @Override
    public java.util.List<String> getSourceCodeFiles() throws Exception {
        java.util.List<String> result;
        synchronized(getClass()) {
            final String batchFile = createBatchFile(getReferenceFile(),
                                                     getArguments());
            result = new BatchFileEnumerator(batchFile).getSourceCodeFiles();
            new java.io.File(batchFile).delete();
        }
        return result;
    }

    public String getArguments() {
        return arguments;
    }

    // private section

    private static String createBatchFile(final String makeFile,
                                          final String arguments)
                                                              throws Exception {
        final String batchFile = "/tmp/tmp_stanse_batch_file_for_makefile.txt";
        final ProcessBuilder builder = new ProcessBuilder(createMakeCmdLine(
                                                                    arguments));
        final java.util.Map<String,String> environment = builder.environment();
        environment.put("JOB_FILE",batchFile);
        environment.put("PATH", environment.get("PATH") + ":" +
                                cz.muni.stanse.Stanse.getRootDirectory());
        builder.directory(new java.io.File(makeFile).getParentFile())
               .start()
               .waitFor();
        return batchFile;
    }

    private static java.util.LinkedList<String> createMakeCmdLine(
                                                            final String args) {
        final java.util.LinkedList<String> result =
                 cz.muni.stanse.utils.Make.<String>linkedList("make","CC=stcc");
        if (!args.isEmpty())
            result.addAll(java.util.Arrays.asList(args.split("[ \t]")));
        return result;
    }

    private final String arguments;
}
