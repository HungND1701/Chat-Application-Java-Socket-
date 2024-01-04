package event;

import model.ReceiveMessage;
import model.SendMessage;

public interface EventChat {
    public void sendMessage(SendMessage data);
    public void receiveMessage(ReceiveMessage data);
}
