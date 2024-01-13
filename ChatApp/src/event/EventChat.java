package event;

import java.util.List;
import model.Conversation;
import model.Receive_Message;
import model.Send_Message;
import model.Send_Message_Group;

public interface EventChat {
    public void sendMessageGroup(Send_Message_Group data);
    public void sendMessage(Send_Message data);
    public void receiveMessage(Receive_Message data);
    public void receiveMessageGroup(int conversation_id, Receive_Message message);
    public void initMessage(List<Send_Message> list);
    public void initMessageGroup(List<Send_Message_Group> list);
}
