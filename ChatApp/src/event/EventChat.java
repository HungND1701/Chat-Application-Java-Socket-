package event;

import java.util.List;
import model.Conversation;
import model.Receive_Message;
import model.Send_Message;

public interface EventChat {
    public void sendMessage(Send_Message data);
    public void receiveMessage(Receive_Message data);
    public void initMessage(List<Send_Message> list);
}
