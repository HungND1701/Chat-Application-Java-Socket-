package event;

import model.User;

public interface EventMain {
    public void showLoading(boolean show);
    public void initChat();
    public void selectUser(User user);
    public void updateUser(User user);
}
