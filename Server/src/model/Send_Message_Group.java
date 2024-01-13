
package model;

import java.util.List;

public class Send_Message_Group {
    private int conversation_id;
    private int userID;
    private List<Integer> userIdList;
    private String text;
    private String time;
    private int messageType;

    public int getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(int conversation_id) {
        this.conversation_id = conversation_id;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
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

    public Send_Message_Group(int conversation_id, int userID, List<Integer> userIdList, String text, String time, int messageType) {
        this.conversation_id = conversation_id;
        this.userID = userID;
        this.userIdList = userIdList;
        this.text = text;
        this.time = time;
        this.messageType = messageType;
    }

    public Send_Message_Group() {
    }

    @Override
    public String toString() {
        return "Send_Message_Group{" + "conversation_id=" + conversation_id + ", userID=" + userID + ", userIdList=" + userIdList + ", text=" + text + ", time=" + time + ", messageType=" + messageType + '}';
    }

}
