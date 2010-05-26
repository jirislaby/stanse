package cz.muni.stanse.lockchecker;

import cz.muni.stanse.checker.CheckerError;


/**
 * Class holding the CheckerError + some additional info needed
 * for filtering 
 * 
 * @author Radim Cebis
 *
 */
class CheckerErrorHolder {	
	
	private CheckerError error;
	private String variable;
	
	public CheckerError getError() {
		return error;
	}

	public void setError(CheckerError error) {
		this.error = error;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}
}
