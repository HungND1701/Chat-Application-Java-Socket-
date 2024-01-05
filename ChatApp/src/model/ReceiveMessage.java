package model;

import app.MessageType;
import org.json.JSONException;
import org.json.JSONObject;

public class ReceiveMessage {
    
    private int fromUserID;
    private String text;
    private MessageType messageType;

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

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public ReceiveMessage() {
    }

    public ReceiveMessage(int fromUserID, String text, MessageType messageType) {
        this.fromUserID = fromUserID;
        this.text = text;
        this.messageType = messageType;
    }
    
    
    
    public ReceiveMessage(Object json){
        JSONObject obj = (JSONObject) json;
        try {
            messageType = MessageType.toMessageType(obj.getInt("messageType"));
            fromUserID = obj.getInt("fromUserID");
            text = obj.getString("text");
        } catch (JSONException e) {
            System.err.println(e);
        }
    }
    
    public JSONObject toJSONObject(){
        try {
            JSONObject json = new JSONObject();
            json.put("messageType", messageType.getValue());
            json.put("fromUserID", fromUserID);
            json.put("text", text);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
    
}
