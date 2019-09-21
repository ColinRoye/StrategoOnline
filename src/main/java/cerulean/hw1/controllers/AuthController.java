package cerulean.hw1.controllers;

import cerulean.hw1.auth.JWTTokenProvider;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for account management API endpoints
 *
 * As of now /login is omitted because I'm using the default form login
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private MongoDBUserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

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

    @PostMapping(value="/login")
    public ResponseEntity signin(@RequestParam String username, @RequestParam String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String token = jwtTokenProvider.createToken(username);
            Map<String, String> result = new HashMap<String, String>();
            result.put("username", username);
            result.put("token", token);
            return ResponseEntity.ok(result);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid credentials supplied");
        }
    }
}
