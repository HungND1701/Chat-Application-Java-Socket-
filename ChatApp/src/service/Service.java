package service;

import app.MessageType;
import event.PublicEvent;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import model.Group;
import model.Receive_Message;
import model.Send_Message;
import model.Send_Message_Group;
import model.User;

public class Service {
    private static Service instance;
    private Socket client;
    private final int PORT_NUMBER = 9999;
    private final String IP = "localhost";
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    public static Service getInstance(){
        if(instance == null){
            instance = new Service();
        }
        return instance;
    }
    
    private Service( ){

    }
    
    public void startServer(){
        try {
            client = IO.socket("http://"+ IP + ":" + PORT_NUMBER);
//            client = IO.socket("https://a923-58-187-77-68.ngrok-free.app");
            client.on("list_user", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    List<User> users = new ArrayList<>();
                    for(Object o: os){
                        User u = new User(o);
                        if(u.getID() != user.getID()){
                            users.add(u);
                        }
                        
                    }
                    PublicEvent.getInstance().getEventMenuLeft().newUser(users);
                    PublicEvent.getInstance().getEventMenuRight().newUser(user, new ArrayList<>());
                }
            });
            client.on("list_friend", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    List<User> friends = new ArrayList<>();
                    for(Object o: os){
                        User u = new User(o);
                        friends.add(u);
                    }
                    PublicEvent.getInstance().getEventMenuLeft().setFriend(friends);
                }
            });
            client.on("other_user", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    List<User> otherUsers = new ArrayList<>();
                    for(Object o: os){
                        User u = new User(o);
                        otherUsers.add(u);
                    }
                    PublicEvent.getInstance().getEventMenuLeft().setOtherUser(otherUsers);
                    PublicEvent.getInstance().getEventMenuRight().setOtherUser(otherUsers);
                }
            });
            client.on("list_group", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    List<Group> listGroup = new ArrayList<>();
                    for(Object o: os){
                        System.out.println(o.toString());
                        Group gr = new Group(o);
                        System.out.println(gr.toString());
                        listGroup.add(gr);
                    }
                    PublicEvent.getInstance().getEventMenuLeft().setGroup(listGroup);

                }
            });
            client.on("user_status", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    User u = new User(os[0]);
                    boolean status = (Boolean) os[1];
                    if(status){
                        PublicEvent.getInstance().getEventMenuLeft().userConnect(u);
                        PublicEvent.getInstance().getEventMenuRight().userConnect(u);
                    }else{
                        PublicEvent.getInstance().getEventMenuLeft().userDisconnect(u);
                        PublicEvent.getInstance().getEventMenuRight().userDisconnect(u);
                    }
                }
            });
            client.on("receive_message", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    Receive_Message message = new Receive_Message(os[0]);
                    PublicEvent.getInstance().getEventChat().receiveMessage(message);
                }
            });
            client.on("receive_message_group", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    Receive_Message message = new Receive_Message(os[0]);
                    System.out.println(os[0].toString());
                    System.out.println(os[1].toString());
                    int conversation_id = (Integer) os[1];
                    PublicEvent.getInstance().getEventChat().receiveMessageGroup(conversation_id, message);
                }
            });
            client.on("list_message", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    List<Send_Message> list = new ArrayList<>();
                    for(Object o: os){
                        Send_Message ms = new Send_Message(o);
                        list.add(ms);
                    }
                    PublicEvent.getInstance().getEventChat().initMessage(list);
                }
            });
            client.on("list_group_message", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    List<Send_Message_Group> list = new ArrayList<>();
                    for(Object o: os){
                        Send_Message_Group ms = new Send_Message_Group(o);
                        list.add(ms);
                    }
                    PublicEvent.getInstance().getEventChat().initMessageGroup(list);
                }
            });
            client.on("new_conversation", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    List<User> users = new ArrayList<>();
                    for(Object o: os){
                        User u = new User(o);   
                        users.add(u);
                    }
                    PublicEvent.getInstance().getEventMenuLeft().newConversation(users);
                }
            });
            client.on("accept_friend_success", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    int userID = (Integer) os[0];
                    PublicEvent.getInstance().getEventMenuLeft().updateAddFriend(userID);
                    PublicEvent.getInstance().getEventMenuRight().removeOtherUserList(userID);
                    Send_Message message = new Send_Message(user.getID(), userID, "Accept", getTimeNow(), MessageType.TEXT);
                    client.emit("send_to_user", message.toJSONObject());
                    PublicEvent.getInstance().getEventChat().sendMessage(message);
                }
            });
            client.on("is_accepted_friend", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    int userID = (Integer) os[0];
                    PublicEvent.getInstance().getEventMenuLeft().updateAddFriend(userID);
                    PublicEvent.getInstance().getEventMenuRight().removeOtherUserList(userID);
                }
            });
            client.on("unfriend_success", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    User u = new User(os[0]);
                    PublicEvent.getInstance().getEventMenuLeft().updateUnfriend(u.getID());
                    PublicEvent.getInstance().getEventMenuRight().addOtherUserList(u);
                    Send_Message message = new Send_Message(user.getID(), u.getID(), "Unfriend", getTimeNow(), MessageType.TEXT);
                    client.emit("send_to_user", message.toJSONObject());
                    PublicEvent.getInstance().getEventChat().sendMessage(message);
                }
            });
            client.on("is_unfriended", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    User u = new User(os[0]);
                    PublicEvent.getInstance().getEventMenuLeft().updateUnfriend(u.getID());
                    PublicEvent.getInstance().getEventMenuRight().addOtherUserList(u);
                }
            });
            client.open();
        } catch (URISyntaxException ex) {
            error(ex);
        }
    }
    
    private void error(Exception e){
        System.out.println(e);
    }
    public String getTimeNow(){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentTime.format(formatter);
        return formattedDateTime;
    }
    public static String getTimeOffline(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime pastTime = LocalDateTime.parse(time, formatter);
        LocalDateTime now = LocalDateTime.now();

        long minutes = ChronoUnit.MINUTES.between(pastTime, now);
        long hours = ChronoUnit.HOURS.between(pastTime, now);
        long days = ChronoUnit.DAYS.between(pastTime, now);

        if (minutes < 1) {
            return "a few seconds";
        } else if (minutes < 60) {
            return minutes + " minutes";
        } else if (hours < 24) {
            return hours + " hours";
        } else {
            return days + " days";
        }
    }
    public Socket getClient() {
        return client;
    }
}
