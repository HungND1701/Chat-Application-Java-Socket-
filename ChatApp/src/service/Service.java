package service;

import event.PublicEvent;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
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
                        if (u.getID() != user.getID()){
                            users.add(u);
                        }
                    }
                    PublicEvent.getInstance().getEventMenuLeft().newUser(users);
                }
            });
            client.on("user_status", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                   int ID = (Integer) os[0];
                   boolean isOnline = (Boolean) os[1];
                   if (isOnline){
                       PublicEvent.getInstance().getEventMenuLeft().userConnect(ID);
                   } else{
                       PublicEvent.getInstance().getEventMenuLeft().userDisconnect(ID);
                   }
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
    
    
    public Socket getClient() {
        return client;
    }
}
