package event;

import java.util.List;
import model.User;

public interface EventMenuLeft {
    public void setOtherUser(List<User> users);
    public void setFriend(List<User> users);
    public void newUser(List<User> users);
    public void userConnect(int userID);
    public void userDisconnect(int userID);
    public void newConversation(List<User> users);
    public void updateAddFriend(int userID);
    public void updateUnfriend(int userID);
}
