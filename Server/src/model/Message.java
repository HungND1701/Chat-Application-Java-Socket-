package model;

public class Message {

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

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
    private Object data;
    
}
