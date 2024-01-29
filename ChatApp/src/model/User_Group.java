package model;

import org.json.JSONException;
import org.json.JSONObject;

public class User_Group {
    private int userId;
    private String username;
    private int groupId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public User_Group() {
    }

    public User_Group(int userId, int groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public User_Group(String username, int groupId) {
        this.username = username;
        this.groupId = groupId;
    }
    
    public JSONObject toJSONObject(){
        try {
            JSONObject json = new JSONObject();
            json.put("userId", userId);
            json.put("username", username);
            json.put("groupId", groupId);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
