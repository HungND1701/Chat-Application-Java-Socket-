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
import model.Login;
import model.Message;
import model.Register;
import model.Send_Message;
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
                rs.close();
                p.close();
                System.out.println("4");
                con.commit();
                con.setAutoCommit(true);
                
                message.setAction(true);
                message.setMessage("Ok");
                user.setID(userID);
                user.setOnline(true);
                user.setAvatar("");
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
        PreparedStatement p = con.prepareStatement("SELECT id, username, nickname, avatar FROM users WHERE id <> ?");
        p.setInt(1, exitUser);
        ResultSet rs = p.executeQuery();
        while(rs.next()){
            int id = rs.getInt(1);
            list.add(new User(id ,rs.getString(2) ,rs.getString(3),rs.getString(4), checkUserStatus(id)));
        }
        rs.close();
        p.close();
        return list;
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
    public void addMessage(Send_Message data) {
        System.out.println("1");
        try {
            System.out.println("2");
            int fromUserID = data.getFromUserID();
            int toUserID = data.getToUserID();
            String text = data.getText();
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
                p = con.prepareStatement("INSERT INTO messages(conversation_id, sender_id, receiver_id, content, send_time) VALUES(?,?,?,?,?)");
                p.setInt(1, conversation_id);
                p.setInt(2, fromUserID);
                p.setInt(3, toUserID);
                p.setString(4, text);
                p.setTimestamp(5, timestamp);
                p.executeUpdate();
                System.out.println("6");
                p.close();
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
                p = con.prepareStatement("INSERT INTO messages(conversation_id, sender_id, receiver_id, content, send_time) VALUES(?,?,?,?,?)");
                p.setInt(1, conversation_id);
                p.setInt(2, fromUserID);
                p.setInt(3, toUserID);
                p.setString(4, text);
                p.setTimestamp(5, timestamp);
                p.executeUpdate();
                System.out.println("10");
                p.close();
                con.commit();
                con.setAutoCommit(true);
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
    }
    
    public List<Send_Message> getMessagesConversation(Conversation data){
        List<Send_Message> listMessages = new ArrayList<>();
        try {
            int sender_id = data.getSender_id();
            int receiver_id = data.getReceiver_id();
            PreparedStatement p = con.prepareStatement("SELECT conversations.id, sender_id, receiver_id, content, send_time FROM messages INNER JOIN conversations ON conversations.id = messages.conversation_id WHERE is_group = 0 AND((sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?)) ORDER BY send_time ASC;");
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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = timestamp.toLocalDateTime().format(formatter);
                Send_Message message = new Send_Message(fromUserID, toUserID, text, formattedDateTime);
                listMessages.add(message);
            }
            return listMessages;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
    public User verifyUser(String userName, String password) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        (rs.getInt(6) != 0));
            }else return null;
            
        } catch (SQLException e) {
        }
        return null;
    }
    
    public User checkUser(String userName) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM users WHERE username = ?");
            preparedStatement.setString(1, userName);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        (rs.getInt(6) != 0));
            }else return null;
            
        } catch (SQLException e) {
        }
        return null;
    }
    
    public User getUserByID(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM users\n"
                    + "WHERE id=?");
            preparedStatement.setInt(1, ID);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        (rs.getInt(6) != 0));

            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public void addUser(User user) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO users(username, password, nickname, avatar)\n"
                    + "VALUES(?,?,?,?)");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getNickname());
            preparedStatement.setString(4, user.getAvatar());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void addUserWithUsernamePasswordNickname(User user) throws SQLException {
        PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO users(username, password, nickname)\n"
                + "VALUES(?,?,?)");
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getNickname());
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
    }

    public boolean checkDuplicated(String username) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public void updateToOnline(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE users\n"
                    + "SET isOnline = 1\n"
                    + "WHERE id = ?");
            preparedStatement.setInt(1, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateToOffline(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE users\n"
                    + "SET isOnline = 0\n"
                    + "WHERE id = ?");
            preparedStatement.setInt(1, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<User> getListFriend(int ID) {
        List<User> ListFriend = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT id, nickname, isOnline\n"
                    + "FROM users\n"
                    + "WHERE id IN (\n"
                    + "	SELECT user_id_2\n"
                    + "    FROM friend\n"
                    + "    WHERE user_id_2 = ?\n"
                    + ")\n"
                    + "OR id IN(\n"
                    + "	SELECT user_id_2\n"
                    + "    FROM friend\n"
                    + "    WHERE user_id_1 = ?\n"
                    + ")");
            preparedStatement.setInt(1, ID);
            preparedStatement.setInt(2, ID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ListFriend.add(new User(rs.getInt(1),  // ID
                        rs.getString(2),
                        (rs.getInt(3) == 1)));
            }
            ListFriend.sort(new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    if (o1.isOnline() && !o2.isOnline())
                        return -1;
                    if (o1.isOnline() && !o2.isOnline())
                        return -1;
                    return 0;
                }

            });
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ListFriend;
    }

    public boolean checkIsFriend(int ID1, int ID2) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT *\n"
                    + "FROM friend\n"
                    + "WHERE (user_id_1 = ? AND user_id_2 = ?)\n"
                    + "OR (user_id_1 = ? AND user_id_2 = ?)");
            preparedStatement.setInt(1, ID1);
            preparedStatement.setInt(2, ID2);
            preparedStatement.setInt(3, ID2);
            preparedStatement.setInt(4, ID1);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public void addFriendShip(int ID1, int ID2) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO friend(user_id_1, user_id_2)\n" +
                    "VALUES (?,?)");
            preparedStatement.setInt(1, ID1);
            preparedStatement.setInt(2, ID2);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removeFriendship(int ID1, int ID2) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM friend\n" +
                    "WHERE (user_id_1 = ? AND user_id_2 = ?)\n" +
                    "OR(user_id_1 = ? AND user_id_2 = ?)");
            preparedStatement.setInt(1, ID1);
            preparedStatement.setInt(2, ID2);
            preparedStatement.setInt(3, ID2);
            preparedStatement.setInt(4, ID1);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void leaveConversation(int ConID, int UserID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM user_conversation\n" +
                    "WHERE (user_id = ? AND conversation_id = ?)\n");
            preparedStatement.setInt(1, UserID);
            preparedStatement.setInt(2, ConID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
}
