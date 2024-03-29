package model;

public class User {
    private int ID;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private boolean isOnline;
    private String last_online;

    public User() {
    }
    public User(int ID, String username, String nickname, String avatar, boolean isOnline, String last_online) {
        this.ID = ID;
        this.username = username;
        this.nickname = nickname;
        this.avatar = avatar;
        this.isOnline = isOnline;
        this.last_online = last_online;
    }

    public User(int ID, String username, String nickname, String avatar, boolean isOnline) {
        this.ID = ID;
        this.username = username;
        this.nickname = nickname;
        this.avatar = avatar;
        this.isOnline = isOnline;
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

    public void setID(int ID) {
        this.ID = ID;
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

    public String getLast_online() {
        return last_online;
    }

    public void setLast_online(String last_online) {
        this.last_online = last_online;
    }

    @Override
    public String toString() {
        return "User{" + "ID=" + ID + ", username=" + username + ", password=" + password + ", nickname=" + nickname + ", avatar=" + avatar + ", isOnline=" + isOnline + ", last_online=" + last_online + '}';
    } 
    
}