package service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;
import model.Client;
import model.Login;
import model.Message;
import model.ReceiveMessage;
import model.Register;
import model.SendMessage;
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
                if (login != null){
                    ar.sendAckData(true, login);
                    addClient(sioc, login);
                    userConnect(login.getID());
                } else {
                    ar.sendAckData(false);
                }
            }
        });
        
        server.addEventListener("list_user", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient sioc, Integer t, AckRequest ar) throws Exception {
                try {
                    List<User> list = serviceUser.getUserOnline(t);
                    sioc.sendEvent("list_user", list.toArray());
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        });
        server.addEventListener("send_to_user", SendMessage.class, new DataListener<SendMessage>() {
            @Override
            public void onData(SocketIOClient sioc, SendMessage t, AckRequest ar) throws Exception {
                sendToClient(t);
            }
        });
        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient sioc) {
                int ID = removeClient(sioc);
                if (ID != 0){
                    userDisconnect(ID);
                }
            }
        });
        server.start();
        textArea.append("\nServer has Start on port : " + PORT_NUMBER + "\n");
    }
    
    private void userConnect(int ID){
        server.getBroadcastOperations().sendEvent("user_status", ID, true);
    }
    
    private void userDisconnect(int ID){
        server.getBroadcastOperations().sendEvent("user_status", ID, false);
    }
    
    private void addClient(SocketIOClient client, User user){
        listClient.add(new Client(client, user));
    }
    private void sendToClient(SendMessage data){
        for(Client c : listClient){
            if(c.getUser().getID() == data.getToUserID()){
                c.getClient().sendEvent("receive_ms", new ReceiveMessage(data.getFromUserID(), data.getText()));
            }
        }
    }
    public int removeClient(SocketIOClient client){
        for (Client d : listClient){
            if (d.getClient() == client){
                listClient.remove(d);
                return d.getUser().getID();
            }
        }
        return 0;
    }
    
    public List<Client> getListClient(){
        return listClient;
    }
}
