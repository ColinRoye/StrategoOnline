package cerulean.hw1.Api.Controllers;

import cerulean.hw1.Api.Models.Account;
import cerulean.hw1.Database.MongoDBUserDetailsManager;
import cerulean.hw1.Database.UserRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("api/account")
public class AccountController {
    Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private MongoDBUserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public void register(@RequestParam String username, @RequestParam String password ) {
        UserDetails newUser = User.builder()
                .passwordEncoder(passwordEncoder::encode)
                .username(username)
                .password(password)
                .authorities(new SimpleGrantedAuthority(UserRoles.ROLE_USER))
                .build();
        userDetailsManager.createUser(newUser);
    }
    @RequestMapping(value="/test", method = RequestMethod.POST)
    public String test(@RequestBody String username, String password ) {
        Account account = new Account(username, password);
        System.out.println("test");
        return account.toJson();
    }
}

