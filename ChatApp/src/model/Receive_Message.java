package model;

import app.MessageType;
import org.json.JSONException;
import org.json.JSONObject;

public class Receive_Message {
    private int fromUserID;
    private String text;
    private String time;
    private MessageType messageType;

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
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
    
    public Receive_Message(Object json) {
        JSONObject obj = (JSONObject)json;
        try {
            fromUserID = obj.getInt("fromUserID");
            text = obj.getString("text");
            time = obj.getString("time");
            messageType = MessageType.toMessageType(obj.getInt("messageType"));
        } catch (JSONException e) {
            System.err.println(e);
        }
    }

    public Receive_Message(int fromUserID, String text, String time, MessageType messageType) {
        this.fromUserID = fromUserID;
        this.text = text;
        this.time = time;
        this.messageType = messageType;
    }

    
    public JSONObject toJSONObject(){
        try {
            JSONObject json = new JSONObject();
            json.put("fromUserID", fromUserID);
            json.put("text", text);
            json.put("time", time);
            json.put("messageType", messageType.getValue());
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
