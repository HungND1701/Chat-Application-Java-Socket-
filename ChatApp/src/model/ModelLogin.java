package model;

import org.json.JSONException;
import org.json.JSONObject;

public class ModelLogin {
    private String username;
    private String password;
    
    public ModelLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public JSONObject toJSONObject(){
        try {
            JSONObject obj = new JSONObject();
            obj.put("username", username);
            obj.put("password", password);
            return obj;
        } catch (JSONException e) {
            return null;
        }
    }
}
