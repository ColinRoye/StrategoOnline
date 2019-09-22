package cerulean.hw1.Api.Controllers;

import cerulean.hw1.Api.Models.Sessions.LoginSession;
import cerulean.hw1.Database.AccountRepository;
import cerulean.hw1.Database.LoginSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RequestMapping("/api/auth")
public class LoginController {
    @Autowired
    private LoginSessionRepository loginSessionRepository;
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value ="/login", method = RequestMethod.POST)
    public void login(HttpServletResponse res, @RequestBody String username, String password) {
        if(accountRepository.exists(username, password)){
            UUID loginSessionId = UUID.randomUUID();
            LoginSession loginSession = new LoginSession(username, password, loginSessionId.toString());
            Cookie cookie = new Cookie("loginSessionId", loginSessionId.toString());
            res.addCookie(cookie);
            loginSessionRepository.save(loginSession.toJson());
        }
    }
    @RequestMapping(value ="/logout", method = RequestMethod.POST)
    public void logout(@RequestBody String loginSessionId) {
        loginSessionRepository.remove(loginSessionId);
    }
}
