package service;

import connection.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.Message;
import model.Register;
import model.User;

public class ServiceUser {
    
    private final Connection con;
    
    public ServiceUser(){
        this.con = DatabaseConnection.getInstance().getConnection();
    }
    
    public Message register(Register data){
        Message message = new Message();
        try {
            User userCheck = verifyUser(data.getUserName(), data.getPassword());
            if(userCheck != null){
                message.setAction(false);
                message.setMessage("User Already Exit");
            }else{
                message.setAction(true);
            }

            if(message.isAction()){
                User user = new User(data.getUserName(), data.getPassword(), data.getNickname());
                addUser(user);
                message.setAction(true);
                message.setMessage("Ok");
            }
        } catch (Exception e) {
            message.setAction(false);
            message.setMessage("Server Error");
        }
        return message;
    }
    
    public User verifyUser(String userName, String password) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.first()) {
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
                + "VALUES(?,?)");
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
