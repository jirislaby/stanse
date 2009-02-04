package cz.muni.stanse.utils;

public final class ClassLocation {

    // public section

    public static String get(final Class c) {
        final String url = ClassURL.get(c).toString();
        return (url.startsWith("jar:file:")) ?
                    url.substring("jar:file:".length(), url.lastIndexOf("!")) :
                    url.substring("file:".length(), url.length());
    }

    public static String get(final Object o) {
        return get(o.getClass());
    }

    public static String get(final String className)
                                       throws java.lang.ClassNotFoundException {
        return get(Class.forName(className));
    }

    // private section

    private ClassLocation() {
    }
}
