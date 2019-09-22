package cerulean.hw1.Api.Controllers;

import cerulean.hw1.Api.Models.Account.Account;
import cerulean.hw1.Database.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthRepository accountRepository;

    @RequestMapping(value ="/register", method = RequestMethod.POST)
    public void register(@RequestBody String username, String password) {
        //TODO: check if account exists
        Account newAccount = new Account(username, password);
        accountRepository.save(newAccount);
    }
    @RequestMapping(value ="/login", method = RequestMethod.POST)
    public void login(HttpServletResponse res, @RequestBody String username, String password) {
        UUID loginSession = UUID.randomUUID();
        Cookie cookie = new Cookie("loginSession", loginSession.toString());
        res.addCookie(cookie);
        //TODO: post id to database
    }
    @RequestMapping(value ="/logout", method = RequestMethod.POST)
    public void logout(@RequestBody String loginSession) {
        //TODO: remove session from db
    }
}
