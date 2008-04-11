/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.iti.scv.newchecker;

/**
 *
 * @author xstastn
 */
public class AutomatonTransition {
    private AutomatonState from;
    private AutomatonState to;
    private boolean isError = false;
    // Is end of run
    private boolean isEOR = false;
    
    
    private String errorMessage;
    // Zatim se pouziva pouze XPath vyraz. Casem predelat na rozhrani a udelat obecneji.
    // Null means no the transition leads to an end node (isFinal = true)
    private String trigger = null;
    

    private AutomatonTransition(AutomatonState from) {
        if(from == null) {
            throw new NullPointerException("From state");
        }
        this.from = from;
      
    }
  
    
    
    public static AutomatonTransition getInstance(AutomatonState from, AutomatonState to, String trigger) {
        AutomatonTransition transition = new AutomatonTransition(from);
        transition.setTo(to);
        transition.setTrigger(trigger);
        
        return transition;
    }
    
    
    public static AutomatonTransition getInstanceFinalError(AutomatonState from, String errorMessage) {
        AutomatonTransition transition = new AutomatonTransition(from);
        
        transition.setIsError(true);
        transition.setIsEOR(true);
        transition.setErrorMessage(errorMessage);
        
        return transition;
    }
    
    public static AutomatonTransition getInstanceError(AutomatonState from, String errorMessage, String trigger) {
        AutomatonTransition transition = AutomatonTransition.getInstanceFinalError(from, errorMessage);
        transition.setIsEOR(false);
        transition.setTrigger(trigger);
        
        return transition;
    }
    
    
    // ================================================
    //          Getters and setters
    // ================================================
    
    public AutomatonState getFrom() {
        return from;
    }

    public void setFrom(AutomatonState from) {
        this.from = from;
    }

    public AutomatonState getTo() {
        return to;
    }

    public void setTo(AutomatonState to) {
        this.to = to;
    }

    public boolean isEOR() {
        return isEOR;
    }

    public void setIsEOR(boolean isEOR) {
        this.isEOR = isEOR;
    }
    
    public boolean isError() {
        return isError;
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
        if(trigger == null) {
            this.isEOR = true;
        }
    }
    
    
}
