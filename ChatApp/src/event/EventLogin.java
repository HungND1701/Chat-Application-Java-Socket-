package event;

import model.Message;
import model.Register;

public interface EventLogin {
    public void login();
    public void register(Register data, EventMessage message);
    public void goLogin();
    public void goRegister();
}
