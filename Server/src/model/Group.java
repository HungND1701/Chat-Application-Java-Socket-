package model;

import java.util.List;

public class Group {
    private int id;
    private String name;
    private List<User> listUser;
    private List<User> listUserLeft;
    private boolean isOnline;

    public boolean isOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<User> getListUser() {
        return listUser;
    }

    public void setListUser(List<User> listUser) {
        this.listUser = listUser;
    }
    
    public List<User> getListUserLeft() {
        return listUserLeft;
    }

    public void setListUserLeft(List<User> listUserLeft) {
        this.listUserLeft = listUserLeft;
    }


    public Group() {
    }

    public Group(int id, String name, List<User> listUser, List<User> listUserLeft, boolean isOnline) {
        this.id = id;
        this.name = name;
        this.listUserLeft = listUserLeft;
        this.listUser = listUser;
        this.isOnline = isOnline;
    }

    @Override
    public String toString() {
        return "Group{" + "id=" + id + ", name=" + name + ", listUser=" + listUser + ", listUserLeft=" + listUserLeft + ", isOnline=" + isOnline + '}';
    }
    
}
