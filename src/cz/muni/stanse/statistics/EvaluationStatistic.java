package cz.muni.stanse.statistics;

public interface EvaluationStatistic {

    public void fileStart(final String fileName);
    public void fileEnd();

    public void internalsStart();
    public void internalsEnd();

    public void checkerStart(final String checkerName);
    public void checkerEnd();

}
