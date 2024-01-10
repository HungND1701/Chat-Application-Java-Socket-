package event;

import java.util.List;
import model.User;

public interface EventMenuRight {
    public void newUser(User user, List<User> listMember);
    public void userConnect(int userID);
    public void userDisconnect(int userID);
    public void setOtherUser(List<User> Users);
    public void removeOtherUserList(int userID);
    public void addOtherUserList(User user);
}
