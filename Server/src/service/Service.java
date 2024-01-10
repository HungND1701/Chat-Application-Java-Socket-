package service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JTextArea;
import model.Client;
import model.Conversation;
import model.Friend;
import model.Login;
import model.Message;
import model.Receive_Message;
import model.Register;
import model.Send_Message;
import model.User;

public class Service {
    private static Service instance;
    private SocketIOServer server;
    private ServiceUser serviceUser;
    private List<Client> listClient;
    private JTextArea textArea;
    private final int PORT_NUMBER = 9999;
    
    public static Service getInstance(JTextArea textArea){
        if(instance == null){
            instance = new Service(textArea);
        }
        return instance;
    }
    
    private Service(JTextArea textArea){
        this.textArea = textArea;
        serviceUser = new ServiceUser();
        listClient = new ArrayList<>();
    }
    
    public void startServer(){
        Configuration config = new Configuration();
        config.setPort(PORT_NUMBER);
        server = new SocketIOServer(config);
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient sioc) {
                textArea.append("One client connected\n");
            }
        });
        server.addEventListener("register", Register.class, new DataListener<Register>() {
            @Override
            public void onData(SocketIOClient sioc, Register t, AckRequest ar) throws Exception {
                Message message = serviceUser.register(t);
                ar.sendAckData(message.isAction(), message.getMessage(), message.getData());
                if(message.isAction()){
                    textArea.append("User Register : " + t.getUserName()+ " || Pass : "+ t.getPassword()+ " || nickname : "+ t.getNickname()+"\n");
                    server.getBroadcastOperations().sendEvent("list_user", (User) message.getData());
                    addClient(sioc, (User) message.getData());
                }
            }
        });
        server.addEventListener("login", Login.class, new DataListener<Login>() {
            @Override
            public void onData(SocketIOClient sioc, Login t, AckRequest ar) throws Exception {
                User login = serviceUser.login(t);
                if(login != null){
                    ar.sendAckData(true, login);
                    addClient(sioc, login);
                    userConnect(login.getID());
                }else{
                    ar.sendAckData(false);
                }
            }
        });
        
        server.addEventListener("list_user", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient sioc, Integer t, AckRequest ar) throws Exception {
                try {
                    List<User> list = serviceUser.getAllUserHaveConversation(t);
                    System.out.println(list.toString());
                    sioc.sendEvent("list_user", list.toArray());
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        });
        server.addEventListener("list_friend", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient sioc, Integer t, AckRequest ar) throws Exception {
                try {
                    List<User> list = serviceUser.getFriendList(t);
                    sioc.sendEvent("list_friend", list.toArray());
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        });
        server.addEventListener("other_user", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient sioc, Integer t, AckRequest ar) throws Exception {
                try {
                    List<User> list = serviceUser.getOtherUser(t);
                    sioc.sendEvent("other_user", list.toArray());
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        });
        server.addEventListener("list_message", Conversation.class, new DataListener<Conversation>() {
            @Override
            public void onData(SocketIOClient sioc, Conversation t, AckRequest ar) throws Exception {
                try {
                    System.out.println("Convertion from user : "+ t.getSender_id() +"to User: "+ t.getReceiver_id());
                    List<Send_Message> list = serviceUser.getMessagesConversation(t);
                    System.out.println(Arrays.toString(list.toArray()));
                    sioc.sendEvent("list_message", list.toArray());
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        });
        server.addEventListener("add_friend", Friend.class, new DataListener<Friend>() {
            @Override
            public void onData(SocketIOClient sioc, Friend t, AckRequest ar) throws Exception {
                try {
                    serviceUser.addFriend(t);
                    // send event success
                    sioc.sendEvent("accept_friend_success", t.getUser_id_2());
                    
                    for(Client c: listClient){
                        if(c.getUser().getID() == t.getUser_id_2()){
                            c.getClient().sendEvent("is_accepted_friend", t.getUser_id_1());
                        }
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        });
        server.addEventListener("reject_add_friend", Friend.class, new DataListener<Friend>() {
            @Override
            public void onData(SocketIOClient sioc, Friend t, AckRequest ar) throws Exception {
                try {
                    serviceUser.rejectAddFriend(t);
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        });
        
        server.addEventListener("send_to_user", Send_Message.class, new DataListener<Send_Message>() {
            @Override
            public void onData(SocketIOClient sioc, Send_Message t, AckRequest ar) throws Exception {
                try {
                    boolean IsNewConversation = serviceUser.addMessage(t);
                    sendToClient(t);
                    if(IsNewConversation){
                        sendToClientFlagNewConversation(t);
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        });
        
        server.addDisconnectListener(new DisconnectListener(){
            @Override
            public void onDisconnect(SocketIOClient sioc) {
                int UserID = removeClient(sioc);
                if(UserID!=0){
                    userDisconect(UserID);
                }
            }
            
        });
        server.start();
        textArea.append("\nServer has Start on port : " + PORT_NUMBER + "\n");
    }
    private void userConnect(int userID){
        server.getBroadcastOperations().sendEvent("user_status", userID, true);
    }
    private void userDisconect(int userID){
        server.getBroadcastOperations().sendEvent("user_status", userID, false);
    }
    private void addClient(SocketIOClient client, User user){
        listClient.add(new Client(client, user));
    }
    private void sendToClient(Send_Message data){
        for(Client c: listClient){
            if(c.getUser().getID() == data.getToUserID()){
                c.getClient().sendEvent("receive_message", new Receive_Message(data.getFromUserID(), data.getText(), data.getTime(), data.getMessageType()));
                break;
            }
        }
    }
    private void sendToClientFlagNewConversation(Send_Message data) throws SQLException{
        for(Client c: listClient){
            if(c.getUser().getID() == data.getToUserID()){
                User user = serviceUser.getUserByID(data.getFromUserID());
                System.out.println(user.toString());
                c.getClient().sendEvent("new_conversation", user);
            }
            if(c.getUser().getID() == data.getFromUserID()){
                User user = serviceUser.getUserByID(data.getToUserID());
                System.out.println(user.toString());
                c.getClient().sendEvent("new_conversation", user);
            }
        }
    }
    
    public int removeClient(SocketIOClient client){
        for(Client d: listClient){
            if(d.getClient() == client){
                listClient.remove(d);
                return d.getUser().getID();
            }
        }
        return 0;
    }

    public List<Client> getListClient() {
        return listClient;
    }
    
    
}
