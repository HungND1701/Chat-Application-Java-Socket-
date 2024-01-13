package event;

import java.util.List;
import model.User;

public interface EventMenuRight {
    public void newUser(User user, List<User> listMember);
    public void userConnect(User user);
    public void userDisconnect(User user);
    public void setOtherUser(List<User> Users);
    public void removeOtherUserList(int userID);
    public void addOtherUserList(User user);
}
