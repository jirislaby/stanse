package cz.muni.stanse.codestructures;

public class CFGNodeNumber {
    private static int number;

    public synchronized static int getNext() {
	return number++;
    }
}
