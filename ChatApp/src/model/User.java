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
        try {
            if(obj.has("id")){
                ID = obj.getInt("id"); 
            }
            if(obj.has("username")){
                username = obj.getString("username");
            }
            if(obj.has("password")){
                password = obj.getString("password");
            }
            if(obj.has("nickname")){
                nickname = obj.getString("nickname");
            }
            if(obj.has("avatar")){
                avatar = obj.getString("avatar");
            }
            if(obj.has("online")){
                isOnline = obj.getBoolean("online");
            } 
        } catch (JSONException e) {
            System.err.println(e);
        }
    }
    public User(JSONObject obj) {
        try {
            if(obj.has("id")){
                ID = obj.getInt("id"); 
            }
            if(obj.has("username")){
                username = obj.getString("username");
            }
            if(obj.has("password")){
                password = obj.getString("password");
            }
            if(obj.has("nickname")){
                nickname = obj.getString("nickname");
            }
            if(obj.has("avatar")){
                avatar = obj.getString("avatar");
            }
            if(obj.has("online")){
                isOnline = obj.getBoolean("online");
            } 
        } catch (JSONException e) {
            System.err.println(e);
        }
    }

    @Override
    public String toString() {
        return "User{" + "ID=" + ID + ", username=" + username + ", password=" + password + ", nickname=" + nickname + ", avatar=" + avatar + ", isOnline=" + isOnline + '}';
    }
    
    
}