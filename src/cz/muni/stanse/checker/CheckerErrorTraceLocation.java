package cz.muni.stanse.checker;

public final class CheckerErrorTraceLocation {

    public CheckerErrorTraceLocation(final String unitName,final int lineNumber,
                                     final String description) {
        this.unitName = unitName;
        this.lineNumber = lineNumber;
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                false : isEqualWith((CheckerErrorTraceLocation)obj);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + getUnitName().hashCode();
        hash = 71 * hash + getLineNumber();
        hash = 71 * hash + getDescription().hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return getDescription() + "[file: " + getUnitName() + " , line: " +
               getLineNumber() + "]";
    }

    public String xmlDump(final String tab, final String seek) {
        String result = tab + "<location>\n";
        result += tab + seek + "<unit>" + getUnitName() + "</unit>\n";
        result += tab + seek + "<line>" + getLineNumber() + "</line>\n";
        result += tab + seek + "<description>" + getDescription() +
                               "</description>\n";
        result += tab + "</location>\n";
        return result;
    }

    public String getUnitName() {
        return unitName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEqualWith(final CheckerErrorTraceLocation other) {
        return getUnitName().equals(other.getUnitName()) &&
               getLineNumber() == other.getLineNumber() &&
               getDescription().equals(other.getDescription());
    }

    public boolean isContextLocation() {
        return getDescription().startsWith("<context>");
    }

    // private section

    private final String unitName;
    private final int lineNumber;
    private final String description;
}
