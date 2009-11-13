package cz.muni.stanse.utils.msgformat;

public class ColumnMessageFormatter implements MessageFormatter {

    // public section

    public ColumnMessageFormatter() {
        this("  ",0);
    }

    public ColumnMessageFormatter(final String tabPattern) {
        this(tabPattern,0);
    }

    public ColumnMessageFormatter(final String tabPattern, int initTabs) {
        super();
        assert(initTabs >= 0);
        this.tabPattern = tabPattern;
        linePrefix = "";
        while (initTabs > 0) {
            linePrefix += tabPattern;
            --initTabs;
        }
        isLineBegin = true;
    }

    @Override
    public String write(final String s) {
        String result;
        if (s.endsWith("\n")) {
            result = getBuffer() + s.substring(0,s.length() - 1)
                                    .replace("\n", "\n" + getLinePrefix())
                                 + "\n";
            setBuffer();
        }
        else
            result = getBuffer() + s.replace("\n", "\n" + getLinePrefix());
        return result;
    }

    public final void pushTab() {
        setLinePrefix(getLinePrefix() + getTabPattern());
    }

    public final void popTab() {
        assert(getLinePrefix().length() >= getTabPattern().length());
        setLinePrefix(getLinePrefix().substring(getTabPattern().length()));
    }

    public final int getNumTabs() {
        return getLinePrefix().length() / getTabPattern().length();
    }

    public final String getTabPattern() {
        return tabPattern;
    }

    // private section

    private final String getLinePrefix() {
        return linePrefix;
    }

    private final void setLinePrefix(String linePrefix) {
        this.linePrefix = linePrefix;
    }

    private final String getBuffer() {
        final String result = isLineBegin ? getLinePrefix() : "";
        isLineBegin = false;
        return result;
    }

    private final void setBuffer() {
        isLineBegin = true;
    }

    private final String tabPattern;
    private String linePrefix;
    private boolean isLineBegin;
}
