
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.xml2cfg.CFGNode;
import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation.
 * It is highly recommended to extend this class and overload the {@link #nodeToText} and {@link #errorTraceEdgeToText} methods
 * 
 * @author honza
 */
public class DefaultCheckerError extends CheckerErrorOld {

    private CFGNode node;
    private Set<ErrorTraceOld> errorTraces = new HashSet<ErrorTraceOld>();
    private String description;

    public DefaultCheckerError() {
    }
    
    public DefaultCheckerError(CFGNode node, Set<ErrorTraceOld> errorTraces, String description) {
        this.node = node;
        this.errorTraces = errorTraces;
        this.description = description;
    }
    
    
    
    
    @Override
    public CFGNode getNode() {
        return node;
    }

    @Override
    protected Set<ErrorTraceOld> getErrorTraces() {
        return errorTraces;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Returns empty string. <br />
     * It is recommended to override this.
     * @param node
     * @return Empty string for all inputs
     */
    @Override
    public String nodeToText(CFGNode node) {
        return "";
    }

    /**
     * Returns empty string for all inputs. <br />
     * It is recommended to override this.
     * @param errorTrace
     * @param from
     * @param to
     * @return Empty string for all inputs.
     */
    @Override
    public String errorTraceEdgeToText(ErrorTraceOld errorTrace, CFGNode from, CFGNode to) {
        return "";
    }

}
