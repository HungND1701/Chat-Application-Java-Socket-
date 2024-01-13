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
    
    public Group(Object json) {
        JSONObject obj = (JSONObject)json;
        listUser = new ArrayList<>();
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
        } catch (JSONException e) {
            System.err.println(e);
        }
    }

    @Override
    public String toString() {
        return "Group{" + "id=" + id + ", name=" + name + ", listUser=" + listUser.toString() + ", isOnline=" + isOnline + '}';
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

            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
