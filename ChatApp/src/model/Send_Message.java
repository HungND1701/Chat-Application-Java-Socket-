package model;

import org.json.JSONException;
import org.json.JSONObject;

public class Send_Message {
    int fromUserID;
    int toUserID;
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

    public int getToUserID() {
        return toUserID;
    }

    public void setToUserID(int toUserID) {
        this.toUserID = toUserID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Send_Message(int fromUserID, int toUserID, String text, String time) {
        this.fromUserID = fromUserID;
        this.toUserID = toUserID;
        this.text = text;
        this.time = time;
    }

    public Send_Message() {
    }
    
    public Send_Message(Object json) {
        JSONObject obj = (JSONObject)json;
        try {
            fromUserID = obj.getInt("fromUserID");
            toUserID = obj.getInt("toUserID");
            text = obj.getString("text");
            time = obj.getString("time");
        } catch (JSONException e) {
            System.err.println(e);
        }
    }
    
    public JSONObject toJSONObject(){
        try {
            JSONObject json = new JSONObject();
            json.put("fromUserID", fromUserID);
            json.put("toUserID", toUserID);
            json.put("text", text);
            json.put("time", time);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
