package event;

import model.Model_Login;
import model.Message;
import model.Register;

public interface EventLogin {
    public void login(Model_Login login);
    public void register(Register data, EventMessage message);
    public void goLogin();
    public void goRegister();
}
