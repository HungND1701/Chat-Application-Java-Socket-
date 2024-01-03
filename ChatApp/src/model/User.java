package model;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private int ID;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private boolean isOnline;

    public User() {
    }

    public User(int ID, String username, String password, String nickname, String avatar, boolean isOnline) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.avatar = avatar;
        this.isOnline = isOnline;
    }

    public User(int ID, String nickname, boolean isOnline) {
        this.ID = ID;
        this.nickname = nickname;
        this.isOnline = isOnline;
    }

    public User(int ID, String username, String password) {
        this.ID = ID;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String nickname, String avatar) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.avatar = avatar;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
    

    public int getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
    
    public User(Object json) {
        JSONObject obj  = (JSONObject) json;
        System.out.println(obj.toString());
        try {
            ID = obj.getInt("id"); 
            username = obj.getString("username");
            password = obj.getString("password");
            nickname = obj.getString("nickname");
            avatar = obj.getString("avatar");
            isOnline = obj.getBoolean("online");
        } catch (JSONException e) {
            System.err.println(e);
        }
    }
}