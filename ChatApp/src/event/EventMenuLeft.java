package event;

import java.util.List;
import model.Group;
import model.User;

public interface EventMenuLeft {
    public void setOtherUser(List<User> users);
    public void setFriend(List<User> users);
    public void setGroup(List<Group> groups);
    public void newUser(List<User> users);
    public void userConnect(User user);
    public void userDisconnect(User user);
    public void newConversation(List<User> users);
    public void updateAddFriend(int userID);
    public void updateUnfriend(int userID);
}
