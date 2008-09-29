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
    
    private TransitionTrigger trigger = null;
    

    private AutomatonTransition(AutomatonState from) {
        if(from == null) {
            throw new NullPointerException("From state");
        }
        this.from = from;
      
    }
  
    
    
    public static AutomatonTransition getInstance(AutomatonState from, AutomatonState to, TransitionTrigger trigger) {
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
    
    public static AutomatonTransition getInstanceError(AutomatonState from, String errorMessage, TransitionTrigger trigger) {
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

    public TransitionTrigger getTrigger() {
        return trigger;
    }

    public void setTrigger(TransitionTrigger trigger) {
        this.trigger = trigger;
    }

    @Override
    public String toString() {
        return "With trigger: "+this.trigger;
    }
    
    
    
    
}
