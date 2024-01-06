package model;

public class Receive_Message {
    int fromUserID;
    String text;
    String time;

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
    
    public Receive_Message(int fromUserID, String text, String time) {
        this.fromUserID = fromUserID;
        this.text = text;
        this.time = time;
    }
    
}
