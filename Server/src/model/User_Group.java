package model;


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

    public User_Group(int userId, String username, int groupId) {
        this.userId = userId;
        this.username = username;
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "User_Group{" + "userId=" + userId + ", username=" + username + ", groupId=" + groupId + '}';
    }
    
    
}
