package model;

public class ReceiveMessage {
    
    private int fromUserID;
    private String text;
    private int messageType;    

    public int getFromUserID() {
        return fromUserID;
    }

    public void setFromUserID(int fromUserID) {
        this.fromUserID = fromUserID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public ReceiveMessage() {
    }

    public ReceiveMessage(int messageType, int fromUserID, String text) {
        this.fromUserID = fromUserID;
        this.text = text;
        this.messageType = messageType;
    }
    
    
   
}
