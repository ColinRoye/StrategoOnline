package cerulean.hw1.Api.Models.Sessions;

import java.util.UUID;

public class LoginSession extends Session{
    public String username;
    private String password;
    private String loginSessionId;
    public LoginSession(String username, String password, String loginSessionId) {
        super();
        this.username = username;
        this.password = password;
        this.loginSessionId = loginSessionId;
    }
}
