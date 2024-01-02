package model;

import org.json.JSONException;
import org.json.JSONObject;

public class Register {
    
    private String userName;
    private String password;
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Register(String userName, String password, String nickname) {
        this.userName = userName;
        this.password = password;
        this.nickname = nickname;
    }

    public Register() {
    }
    
    public JSONObject toJsonObject(){
        try {
            JSONObject json = new JSONObject();
            json.put("userName", userName);
            json.put("password", password); 
            json.put("nickname", nickname);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
