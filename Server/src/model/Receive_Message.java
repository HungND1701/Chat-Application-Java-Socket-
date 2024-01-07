package model;

public class Receive_Message {
    private int fromUserID;
    private String text;
    private String time;
    private int messageType;

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

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

    public Receive_Message() {
    }
    
    public Receive_Message(int fromUserID, String text, String time, int messageType) {
        this.fromUserID = fromUserID;
        this.text = text;
        this.time = time;
        this.messageType = messageType;
    }
    
}
