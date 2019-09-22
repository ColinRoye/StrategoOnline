package cerulean.hw1.Api.Controllers;

import cerulean.hw1.Api.Models.Account;
import cerulean.hw1.Database.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value ="/", method = RequestMethod.POST)
    public String getAccount( @RequestBody String username, String password) {
        return accountRepository.findByUsername(username).toJson();
    }
}
