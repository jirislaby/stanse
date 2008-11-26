package cz.muni.stanse.checker;

import java.util.List;

public final class CheckerError {

    // public section

    public CheckerError(String shortDesc, String fullDesc, int level,
                        List<ErrorTrace> traces) {
        this.shortDesc = shortDesc;
        this.fullDesc = fullDesc;
        this.level = level;
        this.traces = traces;
    }

    public int getErrorLevel() {
        return level;
    }
    
    public String getShortDescription() {
        return shortDesc;
    }

    public String getFullDescription() {
        return fullDesc;
    }

    public List<ErrorTrace> getErrorTraces() {
        return traces;
    }

    @Override
    public String toString() {
        String result =
                "CheckerError:\n" +
                "    - shortDesc: " + getShortDescription() + '\n' +
                "    - fullDesc: " + getFullDescription() + '\n' +
                "    - errorLevel: " + getErrorLevel() + '\n';
        for (ErrorTrace trace : getErrorTraces())
            result += trace.toString().replaceAll("\n","\n    ");

        return result;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((fullDesc == null) ? 0 : fullDesc.hashCode());
        result = PRIME * result + level;
        result = PRIME * result + ((shortDesc == null) ? 0 : shortDesc.hashCode());
        result = PRIME * result + ((traces == null) ? 0 : traces.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                false : isEqualWith((CheckerError)obj);
    }

    public boolean isEqualWith(final CheckerError other) {
        return getShortDescription().equals(other.getShortDescription()) &&
               getFullDescription().equals(other.getFullDescription()) &&
               getErrorLevel() == other.getErrorLevel() &&
               getErrorTraces().equals(other.getErrorTraces());
    }

    // private section

    private final String shortDesc;
    private final String fullDesc;
    private final int level;
    private final List<ErrorTrace> traces;
}
