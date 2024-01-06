package event;

import model.User;

public interface EventMain {
    public void showLoading(boolean show);
    public void initChat();
    public void selectUserChat(User user);
    public void updateUserChat(User user);
}
