package cerulean.hw1.Controllers;
import cerulean.hw1.Services.UserRoles;
import cerulean.hw1.Services.MongoDBUserDetailsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping(value="/sessiontest")
    public String sessionTest() {
        UserDetails principalUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principalUser.getUsername();
        return String.format("Hello, %s", username);
    }

}
