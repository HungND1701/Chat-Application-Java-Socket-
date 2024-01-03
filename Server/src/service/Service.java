package service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import javax.swing.JTextArea;
import model.Message;
import model.Register;

public class Service {
    private static Service instance;
    private SocketIOServer server;
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
                Message message = new ServiceUser().register(t);
                textArea.append("action : " + message.isAction()+ " || mess : "+ message.getMessage()+ " || data : "+ message.getData().toString()+"\n");
                ar.sendAckData(message.isAction(), message.getMessage(), message.getData());
                if(message.isAction()){
                    textArea.append("User Register : " + t.getUserName()+ " || Pass : "+ t.getPassword()+ " || nickname : "+ t.getNickname()+"\n");
                }
            }
        });
        server.start();
        textArea.append("\nServer has Start on port : " + PORT_NUMBER + "\n");
    }
}
