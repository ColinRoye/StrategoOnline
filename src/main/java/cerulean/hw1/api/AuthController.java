package cerulean.hw1.api;

import cerulean.hw1.services.MongoDBUserDetailsManager;
import cerulean.hw1.services.UserRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    Logger logger = LoggerFactory.getLogger(AuthController.class);
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

}
