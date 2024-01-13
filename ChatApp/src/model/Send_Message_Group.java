/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import app.MessageType;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Send_Message_Group {
    private int conversation_id;
    private int userID;
    private List<Integer> userIdList;
    private String text;
    private String time;
    private MessageType messageType;

    public int getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(int conversation_id) {
        this.conversation_id = conversation_id;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public List<Integer> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Integer> userIdList) {
        this.userIdList = userIdList;
    }

    public Send_Message_Group(int conversation_id, int userID, List<Integer> userIdList, String text, String time, MessageType messageType) {
        this.conversation_id = conversation_id;
        this.userID = userID;
        this.userIdList = userIdList;
        this.text = text;
        this.time = time;
        this.messageType = messageType;
    }


    
    public Send_Message_Group(Object json) {
        JSONObject obj = (JSONObject)json;
        userIdList = new ArrayList<>();
        try {
            conversation_id = obj.getInt("conversation_id");
            userID = obj.getInt("userID");
            JSONArray array = obj.getJSONArray("userIdList");
            for (int i = 0; i < array.length(); i++) {
                int userId = array.getInt(i);
                userIdList.add(userId);
            }
            text = obj.getString("text");
            time = obj.getString("time");
            messageType = MessageType.toMessageType(obj.getInt("messageType"));
        } catch (JSONException e) {
            System.err.println(e);
        }
    }
    public Send_Message_Group(JSONObject obj) {
        userIdList = new ArrayList<>();
        try {
            conversation_id = obj.getInt("conversation_id");
            userID = obj.getInt("userID");
            JSONArray array = obj.getJSONArray("userIdList");
            for (int i = 0; i < array.length(); i++) {
                int userId = array.getInt(i);
                userIdList.add(userId);
            }
            text = obj.getString("text");
            time = obj.getString("time");
            messageType = MessageType.toMessageType(obj.getInt("messageType"));
        } catch (JSONException e) {
            System.err.println(e);
        }
    }
    
    public JSONObject toJSONObject(){
        try {
            JSONObject json = new JSONObject();
            json.put("conversation_id", conversation_id);
            json.put("userID", userID);
            JSONArray jsonArray = new JSONArray(userIdList);
            json.put("userIdList", jsonArray);
            json.put("text", text);
            json.put("time", time);
            json.put("messageType", messageType.getValue());
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
