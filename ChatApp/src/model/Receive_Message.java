package model;

import org.json.JSONException;
import org.json.JSONObject;

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
    
    public Receive_Message(Object json) {
        JSONObject obj = (JSONObject)json;
        try {
            fromUserID = obj.getInt("fromUserID");
            text = obj.getString("text");
            time = obj.getString("time");
        } catch (JSONException e) {
            System.err.println(e);
        }
    }

    public Receive_Message(int fromUserID, String text, String time) {
        this.fromUserID = fromUserID;
        this.text = text;
        this.time = time;
    }
    
    public JSONObject toJSONObject(){
        try {
            JSONObject json = new JSONObject();
            json.put("fromUserID", fromUserID);
            json.put("text", text);
            json.put("time", time);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
