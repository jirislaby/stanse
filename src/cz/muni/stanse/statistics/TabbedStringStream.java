package cz.muni.stanse.statistics;

public final class TabbedStringStream {

    // public section

    public TabbedStringStream() {
        this("    ");
    }

    public TabbedStringStream(final String tab) {
        this("",tab);
    }

    public TabbedStringStream(final String seek, final String tab) {
        this.seek = seek;
        this.tab = tab;
        stream = new StringBuilder();
        newLine = false;
    }

    public TabbedStringStream push() {
        seek += tab;
        return this;
    }

    public TabbedStringStream pop() {
        assert(seek.length() >= tab.length());
        seek = seek.substring(0,seek.length() - tab.length());
        return this;
    }

    public <T> TabbedStringStream append(T obj) {
        final String s = escapeNewLines(new StringBuilder().append(obj)
                                                           .toString());
        if (!s.isEmpty()) {
            if (newLine)
                stream.append(seek);
            stream.append(s);
            newLine = s.endsWith("\n");
        }
        return this;
    }

    public <T> TabbedStringStream appendln(T obj) {
        append(obj);
        append('\n');
        return this;
    }

    public String getString() {
        return stream.toString();
    }

    // private section

    private String escapeNewLines(final String s) {
        return (s.length() < 2) ? s : s.substring(0, s.length()-1)
                                       .replace("\n", "\n" + seek) +
                                      s.substring(s.length()-1,s.length());
    }

    private String seek;
    private final String tab;
    private final StringBuilder stream;
    private boolean newLine;
}
