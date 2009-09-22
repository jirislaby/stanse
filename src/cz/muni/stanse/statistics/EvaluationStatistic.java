package cz.muni.stanse.statistics;

import cz.muni.stanse.checker.CheckingResult;

public interface EvaluationStatistic {

    public void fileStart(final String fileName);
    public void fileEnd();

    public void internalsStart();
    public void internalsEnd();

    public void checkerStart(final String checkerName);
    public void checkerEnd(final CheckingResult result);

}
