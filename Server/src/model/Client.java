package model;

import com.corundumstudio.socketio.SocketIOClient;

public class Client {
    private SocketIOClient client;
    private User user; 

    public SocketIOClient getClient() {
        return client;
    }

    public void setClient(SocketIOClient client) {
        this.client = client;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Client(SocketIOClient client, User user) {
        this.client = client;
        this.user = user;
    }

    public Client() {
    }
    
    
}
