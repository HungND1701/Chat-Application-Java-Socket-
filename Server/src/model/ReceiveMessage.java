package model;

public class ReceiveMessage {
    private int fromUserID;
    private String text;

    public ReceiveMessage() {
    }

    public ReceiveMessage(int fromUserID, String text) {
        this.fromUserID = fromUserID;
        this.text = text;
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
    
}
