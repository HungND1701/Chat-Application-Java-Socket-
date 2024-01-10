package model;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Group {
    private int id;
    private List<User> listUser;
    private List<Send_Message> listMessage;

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

    public List<Send_Message> getListMessage() {
        return listMessage;
    }

    public void setListMessage(List<Send_Message> listMessage) {
        this.listMessage = listMessage;
    }


    public Group() {
    }

    public Group(int id, List<User> listUser, List<Send_Message> listMessage) {
        this.id = id;
        this.listUser = listUser;
        this.listMessage = listMessage;
    }
    
    public Group(Object json) {
        JSONObject obj = (JSONObject)json;
        try {
            id = obj.getInt("id");
            JSONArray usersArray = obj.getJSONArray("listUser");
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject userJson = usersArray.getJSONObject(i);
                User user = new User(userJson);
                listUser.add(user);
            }

            JSONArray messagesArray = obj.getJSONArray("listMessage");
            for (int i = 0; i < messagesArray.length(); i++) {
                JSONObject messageJson = messagesArray.getJSONObject(i);
                Send_Message message = new Send_Message(messageJson); 
                listMessage.add(message);
            }
        } catch (JSONException e) {
            System.err.println(e);
        }
    }
}
