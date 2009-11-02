package cz.muni.stanse.checker;

public final class CheckingFailed extends CheckingResult {
    public CheckingFailed(final String msg, final String unitName) {
        this.msg = msg;
        this.unit = unitName;
    }
    public String what() {
        return msg;
    }
    public String where() {
        return unit;
    }

    private final String msg;
    private final String unit;
}
