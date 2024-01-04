package event;

import java.util.List;
import model.User;

public interface EventMenuLeft {
    public void newUser(List<User> users);
    public void userConnect(int ID);
    public void userDisconnect(int ID);
}
