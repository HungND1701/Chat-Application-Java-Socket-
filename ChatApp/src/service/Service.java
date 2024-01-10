package service;

import app.MessageType;
import event.PublicEvent;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Receive_Message;
import model.Send_Message;
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
            client.on("user_status", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    int userID = (Integer) os[0];
                    boolean status = (Boolean) os[1];
                    if(status){
                        PublicEvent.getInstance().getEventMenuLeft().userConnect(userID);
                    }else{
                        PublicEvent.getInstance().getEventMenuLeft().userDisconnect(userID);
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
                    PublicEvent.getInstance().getEventMenuLeft().updateFriendList(userID);
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
                    PublicEvent.getInstance().getEventMenuLeft().updateFriendList(userID);
                    PublicEvent.getInstance().getEventMenuRight().removeOtherUserList(userID);
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
    private String getTimeNow(){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentTime.format(formatter);
        return formattedDateTime;
    }
    
    public Socket getClient() {
        return client;
    }
}
