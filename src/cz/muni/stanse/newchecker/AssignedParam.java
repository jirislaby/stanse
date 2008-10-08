
package cz.muni.stanse.newchecker;

import cz.muni.stanse.newchecker.exceptions.AutomatonException;

/**
 * Relation between param and assigned value.
 * @author xstastn
 */
public class AssignedParam {
    
    // Empty automaton param
    private AutomatonParam param;
    // The value
    private String value;
    
    /**
     * Creates a new instance of AssignedParam -- assigns a value to a param
     * @param param Param
     * @param value Value
     * @throws cz.muni.stanse.newchecker.exceptions.AutomatonException if the param is null
     */
    public AssignedParam(AutomatonParam param, String value) throws AutomatonException {
        if(param == null) {
            throw new AutomatonException(new NullPointerException("param is null"));
        }
        this.param = param;
        this.value = value;
        
    }

    public AutomatonParam getParam() {
        return param;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return param.getId()+": "+value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AssignedParam other = (AssignedParam) obj;
        if (this.param != other.param && (this.param == null || !this.param.equals(other.param))) {
            return false;
        }
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.param != null ? this.param.hashCode() : 0);
        hash = 37 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }
    
    
}
