package cz.muni.stanse.automatonchecker;

import java.util.Vector;

final class AutomatonState {

    // public section

    @Override
    public boolean equals(final Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                    false : isEqualWith((AutomatonState)obj);
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((CFG == null) ? 0 : CFG.hashCode());

        result = PRIME * result + ((automatonIDs == null) ?
                                                   0 : automatonIDs.hashCode());

        result = PRIME * result + symbolID;
        return result;
    }

    // package-private section

    AutomatonState(final cz.muni.stanse.xml2cfg.ControlFlowGraph CFG,
                   final int symbolID, final Vector<Integer> automatonIDs) {
        this.CFG = CFG;
        this.symbolID = symbolID;
        this.automatonIDs = automatonIDs;
    }

    AutomatonState(final cz.muni.stanse.xml2cfg.ControlFlowGraph CFG,
                   final int symbolID, final Integer automatonID) {
        this.CFG = CFG;
        this.symbolID = symbolID;
        this.automatonIDs = new Vector<Integer>(1);
        automatonIDs.add(automatonID);
    }

    cz.muni.stanse.xml2cfg.ControlFlowGraph getCFG() {
        return CFG;
    }

    int getSymbolID() {
        return symbolID;
    }

    Vector<Integer> getAutomatonIDs() {
        return automatonIDs;
    }


    boolean isEqualWith(AutomatonState other) {
        return getCFG() == other.getCFG() &&
               getSymbolID() == other.getSymbolID() &&
               getAutomatonIDs().equals(other.getAutomatonIDs());
//               equalIDs(getAutomatonIDs(),other.getAutomatonIDs());  
    }

    // private section
/*
    private static boolean equalIDs(final Vector<Integer> idVec1,
                                    final Vector<Integer> idVec2) {
        if (idVec1.size() != idVec2.size())
            return false;
        for (int i = 0; i < idVec1.size(); ++i)
            if (idVec2.get(i) != -1 && idVec2.get(i) != idVec1.get(i))
                return false;
        return true;
    }
*/  
    private final cz.muni.stanse.xml2cfg.ControlFlowGraph CFG;
    private final int symbolID;
    private final Vector<Integer> automatonIDs;
}
