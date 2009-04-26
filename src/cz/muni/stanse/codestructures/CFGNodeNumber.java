package cz.muni.stanse.codestructures;

public class CFGNodeNumber {
    private static int number;

    public static int getNext() {
	return number++;
    }
}
