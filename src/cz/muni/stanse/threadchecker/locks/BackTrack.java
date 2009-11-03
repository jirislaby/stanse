
package cz.muni.stanse.threadchecker.locks;

/**
 * Class holds CFGId, line of backTrack, description of command and unit
 * filename.
 * @author Jan Kučera
 */
public class BackTrack {
    private Integer CFGNodeID;
    private Integer line;
    private Integer column;
    private String description;
    private String unitFilename;

    public BackTrack(Integer CFGNodeID, Integer line, Integer column,
                     String description, String unitFilename) {
        this.CFGNodeID = CFGNodeID;
        this.line = line;
        this.column = column;
        this.description = description;
        this.unitFilename = unitFilename;
    }

    public Integer getCFGNodeID() {
        return CFGNodeID;
    }

    public void setCFGNodeID(Integer CFGNodeID) {
        this.CFGNodeID = CFGNodeID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLine() {
        return line;
    }

    public Integer getColumn() {
        return column;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public void setColumn(final Integer column) {
        this.column = column;
    }

    public String getUnitFilename() {
        return unitFilename;
    }

    public void setUnitFilename(String unitFilename) {
        this.unitFilename = unitFilename;
    }
}
