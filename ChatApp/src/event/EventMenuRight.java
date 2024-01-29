package event;

import java.util.List;
import model.Group;
import model.User;

public interface EventMenuRight {
    public void newUser(User user, List<User> listMember);
    public void newGroup(Group group);
    public void userConnect(User user);
    public void userDisconnect(User user);
    public void setOtherUser(List<User> Users);
    public void removeOtherUserList(int userID);
    public void addOtherUserList(User user);
    public void addUserGroup(User user, int groupID);
    public void removeUserGroup(int userID, int groupID);
}
