package cz.muni.stanse.statistics;

import cz.muni.stanse.checker.CheckingResult;

public final class DummyEvaluationStatistic implements EvaluationStatistic {

    // public section

    @Override
    public void fileStart(final String fileName) {
    }
    @Override
    public void fileEnd() {
    }

    @Override
    public void internalsStart() {
    }
    @Override
    public void internalsEnd() {
    }

    @Override
    public void checkerStart(final String checkerName) {
    }
    @Override
    public void checkerEnd(final CheckingResult result) {
    }

}
