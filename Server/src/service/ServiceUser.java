package service;

import connection.DatabaseConnection;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.Client;
import model.Conversation;
import model.Friend;
import model.Group;
import model.Login;
import model.Message;
import model.Register;
import model.Send_Message;
import model.Send_Message_Group;
import model.User;

public class ServiceUser {
    
    private final Connection con;
    
    public ServiceUser(){
        this.con = DatabaseConnection.getInstance().getConnection();
    }
    public User login(Login login) throws SQLException{ 
        User data = null;
        PreparedStatement p = con.prepareStatement("SELECT * FROM users WHERE username = BINARY(?) AND password = BINARY(?)");
        p.setString(1, login.getUsername());
        p.setString(2, login.getPassword());
        ResultSet rs = p.executeQuery();
        if(rs.next()){
            data = new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        (rs.getInt(6) != 0));
        }
        rs.close();
        p.close();
        return data;
    }
    
    public Message register(Register data){
        Message message = new Message();
        try {
            PreparedStatement p = con.prepareStatement("SELECT * FROM users WHERE username = ?");
            p.setString(1, data.getUserName());
            ResultSet rs = p.executeQuery();
            System.out.println("1");
            if(rs.next()){
                System.out.println("2");
                message.setAction(false);
                message.setMessage("User Already Exit");
            }else{
                message.setAction(true);
            }
            rs.close();
            p.close();
            if(message.isAction()){
                System.out.println("3");
                con.setAutoCommit(false);
                User user = new User(data.getUserName(), data.getPassword(), data.getNickname());
                p = con.prepareStatement("INSERT INTO users(username, password, nickname) VALUES(?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
                p.setString(1, user.getUsername());
                p.setString(2, user.getPassword());
                p.setString(3, user.getNickname());
                p.executeUpdate();
                rs = p.getGeneratedKeys();
                rs.next();
                int userID = rs.getInt(1);
                String avatar = ((userID % 15) +1) + "";
                rs.close();
                p.close();
                p = con.prepareStatement("UPDATE users SET avatar = ? WHERE id = ?");
                p.setString(1, avatar);
                p.setInt(2, userID);
                p.executeUpdate();
                System.out.println("4");
                con.commit();
                con.setAutoCommit(true);
                message.setAction(true);
                message.setMessage("Ok");
                user.setID(userID);
                user.setOnline(true);
                user.setAvatar(avatar);
                System.out.println(user.toString());
                message.setData(user);
            }
        } catch (SQLException e) {
            System.out.println("5");
            message.setAction(false);
            message.setMessage("Server Error");
            try {
                if(con.getAutoCommit()==false){
                    System.out.println("6");
                    con.rollback();
                    con.setAutoCommit(true);
                }
            } catch (SQLException e1) {
                
            }
        }
        return message;
    }
    
    public List<User> getUser(int exitUser) throws SQLException {
        List<User> list = new ArrayList<>();
        PreparedStatement p = con.prepareStatement("SELECT id, username, nickname, avatar , last_online FROM users WHERE id <> ?");
        p.setInt(1, exitUser);
        ResultSet rs = p.executeQuery();
        while(rs.next()){
            int id = rs.getInt(1);
            list.add(new User(id ,rs.getString(2) ,rs.getString(3),rs.getString(4), checkUserStatus(id), timestampToString(rs.getTimestamp(5))));
        }
        rs.close();
        p.close();
        return list;
    }
    public User getUserByID(int exitUser) throws SQLException {
        User user = new User();
        PreparedStatement p = con.prepareStatement("SELECT id, username, nickname, avatar, last_online FROM users WHERE id = ?");
        p.setInt(1, exitUser);
        ResultSet rs = p.executeQuery();
        if(rs.next()){
            int id = rs.getInt(1);
            user = new User(id ,rs.getString(2) ,rs.getString(3),rs.getString(4), checkUserStatus(id), timestampToString(rs.getTimestamp(5)));
        }
        rs.close();
        p.close();
        return user;
    }
    public User getUserByUsername(String username) throws SQLException {
        User user = new User();
        PreparedStatement p = con.prepareStatement("SELECT id, username, nickname, avatar, last_online FROM users WHERE username = ?");
        p.setString(1, username);
        ResultSet rs = p.executeQuery();
        if(rs.next()){
            int id = rs.getInt(1);
            user = new User(id ,rs.getString(2) ,rs.getString(3),rs.getString(4), checkUserStatus(id), timestampToString(rs.getTimestamp(5)));
        }
        rs.close();
        p.close();
        return user;
    }
    public void updateLastOnline(int userID) throws SQLException{
        PreparedStatement p = con.prepareStatement("UPDATE users SET last_online = ? WHERE id = ?");
        p.setTimestamp(1, getTimestampNow());
        p.setInt(2, userID);
        p.executeUpdate();
    }
    public List<User> getAllUserHaveConversation(int userID) throws SQLException{
        List<User> list = new ArrayList<>();
        PreparedStatement p = con.prepareStatement("SELECT id, username, nickname, avatar, last_online FROM users WHERE id IN (SELECT user_id FROM user_conversation WHERE user_id <> ? AND conversation_id IN(SELECT conversations.id FROM user_conversation JOIN conversations ON user_conversation.conversation_id = conversations.id WHERE is_group = 0 AND user_id = ?))");
        p.setInt(1, userID);
        p.setInt(2, userID);
        ResultSet rs = p.executeQuery();
        while(rs.next()){
            int id = rs.getInt(1);
            list.add(new User(id ,rs.getString(2) ,rs.getString(3),rs.getString(4), checkUserStatus(id), timestampToString(rs.getTimestamp(5))));
        }
        rs.close();
        p.close();
        return list;
    }
    
    public List<User> getFriendList(int exitUser) throws SQLException {
        List<User> list = new ArrayList<>();
        PreparedStatement p = con.prepareStatement("SELECT id, username, nickname, avatar, last_online FROM users WHERE id IN (SELECT IF(friend.user_id_1 = ?, friend.user_id_2, friend.user_id_1) AS id FROM friend WHERE ? IN (friend.user_id_1, friend.user_id_2)) ");
        p.setInt(1, exitUser);
        p.setInt(2, exitUser);
        ResultSet rs = p.executeQuery();
        while(rs.next()){
            int id = rs.getInt(1);
            list.add(new User(id ,rs.getString(2) ,rs.getString(3),rs.getString(4), checkUserStatus(id), timestampToString(rs.getTimestamp(5))));
        }
        rs.close();
        p.close();
        return list;
    }
    public List<User> getOtherUser(int exitUser) throws SQLException {
        List<User> list = new ArrayList<>();
        PreparedStatement p = con.prepareStatement("SELECT id, username, nickname, avatar, last_online FROM users WHERE id <> ? AND id NOT IN (SELECT id FROM users WHERE id IN (SELECT IF(friend.user_id_1 = ?, friend.user_id_2, friend.user_id_1) AS id FROM friend WHERE ? IN (friend.user_id_1, friend.user_id_2))) ");
        p.setInt(1, exitUser);
        p.setInt(2, exitUser);
        p.setInt(3, exitUser);
        ResultSet rs = p.executeQuery();
        while(rs.next()){
            int id = rs.getInt(1);
            list.add(new User(id ,rs.getString(2) ,rs.getString(3),rs.getString(4), checkUserStatus(id), timestampToString(rs.getTimestamp(5))));
        }
        rs.close();
        p.close();
        return list;
    }
    public List<Group> getGroupList(int userID) throws  SQLException {
        List<Group> list = new ArrayList<>();
        PreparedStatement p = con.prepareStatement("SELECT conversations.id, name FROM conversations join user_conversation ON conversations.id = user_conversation.conversation_id WHERE is_group = 1 AND user_id = ?;");
        p.setInt(1, userID);
        ResultSet rs = p.executeQuery();
        while(rs.next()){
            List<User> listUserLeft = new ArrayList<>();
            List<User> listUser = new ArrayList<>();
            int conversation_id = rs.getInt(1);
            String name = rs.getString(2);
            p = con.prepareStatement("SELECT id, username, nickname, avatar, last_online FROM users WHERE id IN(SELECT user_id FROM user_conversation WHERE conversation_id = ?);");
            p.setInt(1, conversation_id);
            ResultSet rs_user = p.executeQuery();
            while(rs_user.next()){
                int id = rs_user.getInt(1);
                listUser.add(new User(id ,rs_user.getString(2) ,rs_user.getString(3),rs_user.getString(4), checkUserStatus(id), timestampToString(rs_user.getTimestamp(5))));
            }
            rs_user.close();            
            
            p = con.prepareStatement("SELECT id, username, nickname, avatar, last_online FROM users WHERE id IN(SELECT DISTINCT sender_id FROM messages WHERE conversation_id = ? AND sender_id NOT IN (SELECT user_id FROM user_conversation WHERE conversation_id = ?));");
            p.setInt(1, conversation_id);
            p.setInt(2, conversation_id);
            ResultSet rs_user_left = p.executeQuery();
            while(rs_user_left.next()){
                int id = rs_user_left.getInt(1);
                listUserLeft.add(new User(id ,rs_user_left.getString(2) ,rs_user_left.getString(3),rs_user_left.getString(4), checkUserStatus(id), timestampToString(rs_user_left.getTimestamp(5))));
            }
            rs_user_left.close(); 
            
            list.add(new Group(conversation_id, name, listUser, listUserLeft, true));
        }
        return list;
    }
    public List<Integer> getListUserIDGroup(int conversation_id) throws SQLException {
        List<Integer> listUserID = new ArrayList<>();
        PreparedStatement p = con.prepareStatement("SELECT id FROM users WHERE id IN(SELECT user_id FROM user_conversation WHERE conversation_id = ?);");
        p.setInt(1, conversation_id);
        ResultSet rs_user = p.executeQuery();
        while(rs_user.next()){
            int id = rs_user.getInt(1);
            listUserID.add(id);
        }
        rs_user.close();
        p.close();
        return listUserID;
    }
    
    public boolean checkUserStatus(int id){
        List<Client> clients = Service.getInstance(null).getListClient();
        for(Client c : clients){
            if(c.getUser().getID()==id){
                return true;
            }
        }
        return false;
    }
    public void addFriend(Friend friend) throws SQLException{
        PreparedStatement p = con.prepareStatement("INSERT INTO friend(user_id_1, user_id_2) VALUES (?,?)");
        p.setInt(1, friend.getUser_id_1());
        p.setInt(2, friend.getUser_id_2());
        p.executeUpdate();
        p.close();
        p = con.prepareStatement("UPDATE messages SET type = ? WHERE sender_id = ? AND receiver_id = ? AND type = 4");
        p.setInt(1, 5);
        p.setInt(2, friend.getUser_id_2());
        p.setInt(3, friend.getUser_id_1());
        p.executeUpdate();
    }
    public void unfriend(Friend friend) throws SQLException{
        PreparedStatement p = con.prepareStatement("DELETE FROM friend WHERE (user_id_1 = ? AND user_id_2 = ?) OR (user_id_1 = ? AND user_id_2 = ?)");
        p.setInt(1, friend.getUser_id_1());
        p.setInt(2, friend.getUser_id_2());
        p.setInt(3, friend.getUser_id_2());
        p.setInt(4, friend.getUser_id_1());
        p.executeUpdate();
    }
    public void rejectAddFriend(Friend friend) throws SQLException{
        PreparedStatement p = con.prepareStatement("UPDATE messages SET type = ? WHERE sender_id = ? AND receiver_id = ? AND type = 4");
        p.setInt(1, 5);
        p.setInt(2, friend.getUser_id_2());
        p.setInt(3, friend.getUser_id_1());
        p.executeUpdate();
    }
    
    public boolean addMessage(Send_Message data) {
        System.out.println("1");
        try {
            System.out.println("2");
            int fromUserID = data.getFromUserID();
            int toUserID = data.getToUserID();
            String text = data.getText();
            int messageType = data.getMessageType();
            String timeString = data.getTime();
            //cover timeString to timestamp type
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(timeString, formatter);
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            //check messages
            System.out.println("3");
            PreparedStatement p = con.prepareStatement("SELECT conversation_id FROM messages WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?)");
            p.setInt(1, fromUserID);
            p.setInt(2, toUserID);
            p.setInt(3, toUserID);
            p.setInt(4, fromUserID);
            ResultSet rs = p.executeQuery();
            System.out.println("4");
            if(rs.next()){// co message -> co conversation -> co user_conversation-> co role_conversation
                System.out.println("5");
                int conversation_id = rs.getInt(1);
                rs.close();
                p.close();
                p = con.prepareStatement("INSERT INTO messages(conversation_id, sender_id, receiver_id, content, send_time, type) VALUES(?,?,?,?,?,?)");
                p.setInt(1, conversation_id);
                p.setInt(2, fromUserID);
                p.setInt(3, toUserID);
                p.setString(4, text);
                p.setTimestamp(5, timestamp);
                p.setInt(6, messageType);
                p.executeUpdate();
                System.out.println("6");
                p.close();
                return false;
            }else{
                rs.close();
                p.close();
                System.out.println("7");
                con.setAutoCommit(false);
                p = con.prepareStatement("INSERT INTO conversations(is_group) VALUES(?)", PreparedStatement.RETURN_GENERATED_KEYS);
                p.setInt(1, 0);
                p.executeUpdate();
                System.out.println("8");
                rs = p.getGeneratedKeys();
                rs.next();
                int conversation_id = rs.getInt(1);
                rs.close();
                p.close();
                p = con.prepareStatement("INSERT INTO user_conversation(conversation_id, user_id, role_id) VALUES(?,?,?), (?,?,?)");
                p.setInt(1, conversation_id);
                p.setInt(2, fromUserID);
                p.setInt(3, 1);
                p.setInt(4, conversation_id);
                p.setInt(5, toUserID);
                p.setInt(6, 1);
                p.executeUpdate();
                p.close();
                System.out.println("9");
                p = con.prepareStatement("INSERT INTO messages(conversation_id, sender_id, receiver_id, content, send_time, type) VALUES(?,?,?,?,?,?)");
                p.setInt(1, conversation_id);
                p.setInt(2, fromUserID);
                p.setInt(3, toUserID);
                p.setString(4, text);
                p.setTimestamp(5, timestamp);
                p.setInt(6, messageType);
                p.executeUpdate();
                System.out.println("10");
                p.close();
                con.commit();
                con.setAutoCommit(true);
                return true;
            }
    
        } catch (SQLException e) {
            System.err.println(e);
            try {
                if(con.getAutoCommit()==false){
                    System.out.println("6");
                    con.rollback();
                    con.setAutoCommit(true);
                }
            } catch (SQLException e1) {
                
            }
        }
        return false;
    }
    public void addMessageToGroup(Send_Message_Group data) {
        try {
            int conversation_id = data.getConversation_id();
            int fromUserID = data.getUserID();
            List<Integer> toUserIDList = data.getUserIdList();
            String text = data.getText();
            int messageType = data.getMessageType();
            String timeString = data.getTime();
            //cover timeString to timestamp type
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(timeString, formatter);
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            //check messages
            con.setAutoCommit(false);
            for(int toUserID : toUserIDList){
                PreparedStatement p = con.prepareStatement("INSERT INTO messages(conversation_id, sender_id, receiver_id, content, send_time, type) VALUES(?,?,?,?,?,?)");
                p.setInt(1, conversation_id);
                p.setInt(2, fromUserID);
                p.setInt(3, toUserID);
                p.setString(4, text);
                p.setTimestamp(5, timestamp);
                p.setInt(6, messageType);
                p.executeUpdate();
                p.close();
            }
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println(e);
            try {
                if(con.getAutoCommit()==false){
                    con.rollback();
                    con.setAutoCommit(true);
                }
            } catch (SQLException e1) {
                
            }
        }
    }
    
    public List<Send_Message> getMessagesConversation(Conversation data){
        List<Send_Message> listMessages = new ArrayList<>();
        try {
            int sender_id = data.getSender_id();
            int receiver_id = data.getReceiver_id();
            PreparedStatement p = con.prepareStatement("SELECT conversations.id, sender_id, receiver_id, content, send_time, type FROM messages INNER JOIN conversations ON conversations.id = messages.conversation_id WHERE is_group = 0 AND((sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?)) ORDER BY send_time ASC;");
            p.setInt(1, sender_id);
            p.setInt(2, receiver_id);
            p.setInt(3, receiver_id);
            p.setInt(4, sender_id);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                int fromUserID = rs.getInt(2);
                int toUserID = rs.getInt(3);
                String text = rs.getString(4);
                Timestamp timestamp = rs.getTimestamp(5);
                int messageType = rs.getInt(6);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = timestamp.toLocalDateTime().format(formatter);
                Send_Message message = new Send_Message(fromUserID, toUserID, text, formattedDateTime, messageType);  // sua type sau 
                listMessages.add(message);
            }
            return listMessages;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
    public List<Send_Message_Group> getMessagesConversationGroup(int conversation_id){
        try {
            List<Send_Message_Group> list = new ArrayList<>();
            PreparedStatement p = con.prepareStatement("SELECT DISTINCT conversation_id, sender_id, content, send_time, type FROM messages WHERE conversation_id = ? ORDER BY send_time ASC;");
            p.setInt(1, conversation_id);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                list.add(new Send_Message_Group(rs.getInt(1),rs.getInt(2), new ArrayList<Integer>(), rs.getString(3), timestampToString(rs.getTimestamp(4)), rs.getInt(5)));
            }
            return list;
        } catch (Exception e) {
            return new ArrayList<>();
        }   
    }
    private static String timestampToString(Timestamp timestamp){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = timestamp.toLocalDateTime().format(formatter);
        return formattedDateTime;
    }
    
    private Timestamp getTimestampNow(){
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        return timestamp;
    }

    public boolean leaveConversation(int ConID, int UserID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM user_conversation\n" +
                    "WHERE (user_id = ? AND conversation_id = ?)\n");
            preparedStatement.setInt(1, UserID);
            preparedStatement.setInt(2, ConID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public boolean addUserToConversation(int ConID, int UserID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO user_conversation (user_id, conversation_id, role_id) VALUES\n" +
                    "(?, ?, 1)");
            preparedStatement.setInt(1, UserID);
            preparedStatement.setInt(2, ConID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
}
