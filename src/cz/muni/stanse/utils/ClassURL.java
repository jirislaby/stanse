package cz.muni.stanse.utils;

public final class ClassURL {

    // public section

    public static java.net.URL get(final Class c) {
        String resourceName = c.getName().replace('.', '/') + ".class";
        ClassLoader classLoader = c.getClassLoader();
        if(classLoader==null) {
          classLoader = ClassLoader.getSystemClassLoader();
        }
        return classLoader.getResource(resourceName);
    }

    public static java.net.URL get(final Object o) {
        return get(o.getClass());
    }

    public static java.net.URL get(final String className)
                                       throws java.lang.ClassNotFoundException {
        return get(Class.forName(className));
    }

    // private section

    private ClassURL() {
    }
}
