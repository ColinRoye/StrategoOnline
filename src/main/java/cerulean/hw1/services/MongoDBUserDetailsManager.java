package cerulean.hw1.services;

import cerulean.hw1.models.Account;
import cerulean.hw1.repositories.AccountRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * This class implements UserDetailsService, which is the interface responsible for loading UserDetails objects given
 * a username.
 * This specific implementation points to a mongoDB database
 *
 * Built on top of this tutorial:
 * https://medium.com/@gtommee97/rest-authentication-with-spring-security-and-mongodb-28c06da25fb1
 */
@Component
public class MongoDBUserDetailsManager implements UserDetailsManager {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new User(
                account.getUsername(),
                account.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(UserRoles.ROLE_USER)));

    }


    @Override
    public void createUser(UserDetails user) {
        // TODO: Add validation
        Account newAccount = new Account(ObjectId.get(), user.getUsername(), user.getPassword());
        accountRepository.insert(newAccount);

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

}
