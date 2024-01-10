package model;

import org.json.JSONException;
import org.json.JSONObject;

public class Friend {
    private int user_id_1;
    private int user_id_2;

    public int getUser_id_1() {
        return user_id_1;
    }

    public void setUser_id_1(int user_id_1) {
        this.user_id_1 = user_id_1;
    }

    public int getUser_id_2() {
        return user_id_2;
    }

    public void setUser_id_2(int user_id_2) {
        this.user_id_2 = user_id_2;
    }

    public Friend() {
    }

    public Friend(int user_id_1, int user_id_2) {
        this.user_id_1 = user_id_1;
        this.user_id_2 = user_id_2;
    }
    
    public JSONObject toJSONObject(){
        try {
            JSONObject json = new JSONObject();
            json.put("user_id_1", user_id_1);
            json.put("user_id_2", user_id_2);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
