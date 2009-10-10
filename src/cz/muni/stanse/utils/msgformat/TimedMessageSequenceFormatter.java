package cz.muni.stanse.utils.msgformat;

public class TimedMessageSequenceFormatter implements MessageFormatter {

    // public section

    public TimedMessageSequenceFormatter(final String prefix,
                                        final String sufix,
                                        final String ellipsis) {
        startTime = System.currentTimeMillis();
        this.prefix = prefix;
        this.sufix = sufix;
        this.ellipsis = ellipsis;
        lastTime = startTime;
        started = false;
    }

    @Override
    public String write(final String s) {
        return getNextTimeMessage() + s + getEllipsis();
    }

    public final String interrupt(final String s) {
        final float deltaTime = (float)(System.currentTimeMillis() -
                                        getStartTime()) / 1000;
        final String timeMsg = getNextTimeMessage();
        setStarted(false);
        return timeMsg + s + deltaTime + getSufix();
    }

    // private section

    private String getNextTimeMessage() {
        String result = "";

        final long currentTime = System.currentTimeMillis();
        if (isStarted()) {
            final float deltaTime = (float)(currentTime - getLastTime()) / 1000;
            result = getPrefix() + deltaTime + getSufix() + "\n";
        }
        setLastTime(currentTime);
        setStarted(true);

        return result;
    }

    private long getStartTime() {
        return startTime;
    }

    private long getLastTime() {
        return lastTime;
    }

    private void setLastTime(final long time) {
        lastTime = time;
    }

    private boolean isStarted() {
        return started;
    }

    private void setStarted(boolean started) {
        this.started = started;
    }

    private String getPrefix() {
        return prefix;
    }

    private String getSufix() {
        return sufix;
    }

    private String getEllipsis() {
        return ellipsis;
    }

    private final long startTime;
    private final String prefix;
    private final String sufix;
    private final String ellipsis;

    private long lastTime;
    private boolean started;
}
