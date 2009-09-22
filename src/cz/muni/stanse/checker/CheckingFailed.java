package cz.muni.stanse.checker;

public final class CheckingFailed extends CheckingResult {
    public CheckingFailed(final String msg) {
        this.msg = msg;
    }
    public String what() {
        return msg;
    }

    private final String msg;
}
