package cz.muni.stanse.lockchecker;


/**
 * Mutable class holding integer. For use in Maps.
 * 
 * @author Radim Cebis 
 */
class Counter {
	int count = 0;
	
	/**
	 * 
	 * @param init initial value of the counter
	 */
	public Counter(int init) {
		count = init;
	}
	
	/**
	 * Gets count of the counter
	 * @return count
	 */
	public int get() {
		return count;
	}

	/**
	 * Adds specified value to the counter
	 * @param value int value to be added
	 */
	public void add(int value) {
		count += value;
	}

	@Override
	public String toString() {
		return Integer.toString(count);
	}
	
	
	
}
