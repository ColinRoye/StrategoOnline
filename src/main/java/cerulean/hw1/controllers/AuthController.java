package cerulean.hw1.controllers;

import cerulean.hw1.models.Account;
import cerulean.hw1.repositories.AccountRepository;
import cerulean.hw1.services.MongoDBUserDetailsManager;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for account management API endpoints
 *
 * As of now /login is omitted because I'm using the default form login
 */
@RestController
public class AuthController {

    @Autowired
    private MongoDBUserDetailsManager userDetailsManager;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public void register(@RequestParam String username, @RequestParam String password ) {
        UserDetails newUser = User.builder()
                .username(username)
                .password(password)
                .build();

        userDetailsManager.createUser(newUser);
    }
}
