package event;

import model.Register;
import model.ModelLogin;

public interface EventLogin {
    public void login(ModelLogin data);
    public void register(Register data, EventMessage message);
    public void goLogin();
    public void goRegister();
}
