package service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import java.util.List;
import javax.swing.JTextArea;
import model.Message;
import model.Register;
import model.User;

public class Service {
    private static Service instance;
    private SocketIOServer server;
    private ServiceUser serviceUser;
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
        server.start();
        textArea.append("\nServer has Start on port : " + PORT_NUMBER + "\n");
    }
}
