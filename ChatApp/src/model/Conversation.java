package model;

import org.json.JSONException;
import org.json.JSONObject;

public class Conversation {
    private int sender_id;
    private int receiver_id;

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public Conversation() {
    }

    public Conversation(int sender_id, int receiver_id) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
    }
    public JSONObject toJSONObject(){
        try {
            JSONObject json = new JSONObject();
            json.put("sender_id", sender_id);
            json.put("receiver_id", receiver_id);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
