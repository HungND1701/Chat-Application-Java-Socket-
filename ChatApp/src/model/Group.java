package model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    
    public Group(Object json) {
        JSONObject obj = (JSONObject)json;
        listUser = new ArrayList<>();
        listUserLeft = new ArrayList<>();
        try {
            id = obj.getInt("id");
            name = obj.getString("name");
            isOnline = obj.getBoolean("online");
            JSONArray usersArray = obj.getJSONArray("listUser");
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject userJson = usersArray.getJSONObject(i);
                User user = new User(userJson);
                listUser.add(user);
            }
            JSONArray usersLeftArray = obj.getJSONArray("listUserLeft");
            for (int i = 0; i < usersLeftArray.length(); i++) {
                JSONObject userJson = usersLeftArray.getJSONObject(i);
                User user = new User(userJson);
                listUserLeft.add(user);
            }
        } catch (JSONException e) {
            System.err.println(e);
        }
    }

    @Override
    public String toString() {
        return "Group{" + "id=" + id + ", name=" + name + ", listUser=" + listUser + ", listUserLeft=" + listUserLeft + ", isOnline=" + isOnline + '}';
    }

    
    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
            json.put("name", name);
            json.put("isOnline", isOnline);

            JSONArray jsonArray = new JSONArray();
            for (User user : listUser) {
                jsonArray.put(user.toJSONObject());
            }
            json.put("listUser", jsonArray);
            
            JSONArray jsonArray2 = new JSONArray();
            for (User user : listUserLeft) {
                jsonArray2.put(user.toJSONObject());
            }
            json.put("listUserLeft", jsonArray2);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
