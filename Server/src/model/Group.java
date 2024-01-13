package model;

import java.util.List;

public class Group {
    private int id;
    private String name;
    private List<User> listUser;
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

    public Group() {
    }

    public Group(int id, String name, List<User> listUser, boolean isOnline) {
        this.id = id;
        this.name = name;
        this.listUser = listUser;
        this.isOnline = isOnline;
    }
    

    @Override
    public String toString() {
        return "Group{" + "id=" + id + ", name=" + name + ", listUser=" + listUser.toString() + ", isOnline=" + isOnline + '}';
    }
}
