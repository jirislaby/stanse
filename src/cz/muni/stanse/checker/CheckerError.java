package cz.muni.stanse.checker;

import java.util.List;

public final class CheckerError {
    
    public enum ErrorLevel {
        ERROR,
        WARNING,
        HINT;
    }

    public CheckerError(String shortDesc, String fullDesc, ErrorLevel level,
                        List<ErrorTrace> traces) {
        this.shortDesc = shortDesc;
        this.fullDesc = fullDesc;
        this.level = level;
        this.traces = traces;
    }

    public ErrorLevel getErrorLevel() {
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

    private final String shortDesc;
    private final String fullDesc;
    private final ErrorLevel level;
    private final List<ErrorTrace> traces;
}

// TODO: There is possibility to extract ErrorLevel out from the CheckerError
//       class. The only thing which should be done is to introduce Java
//       container to class Checker, which corresponds to C++ multimap.
