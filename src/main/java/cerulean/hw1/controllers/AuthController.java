package cerulean.hw1.controllers;

import cerulean.hw1.config.WebSecurityConfig;
import cerulean.hw1.models.Account;
import cerulean.hw1.repositories.AccountRepository;
import cerulean.hw1.services.MongoDBUserDetailsManager;
import cerulean.hw1.services.UserRoles;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


/**
 * Controller for account management API endpoints
 *
 * As of now /login is omitted because I'm using the default form login
 */
@RestController
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private MongoDBUserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value="/register", method = RequestMethod.POST, consumes = {"application/json"})
    public void register(@RequestBody Account account) {
        UserDetails newUser = User.builder()
                .passwordEncoder(passwordEncoder::encode)
                .username(account.getUsername())
                .password(account.getPassword())
                .authorities(new SimpleGrantedAuthority(UserRoles.ROLE_USER))
                .build();
        userDetailsManager.createUser(newUser);
    }

}
