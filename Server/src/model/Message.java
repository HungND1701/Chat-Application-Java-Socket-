package model;

public class Message {

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message(boolean action, String message) {
        this.action = action;
        this.message = message;
    }

    public Message() {
        
    }
    
    private boolean action;
    private String message;
    
    
}
