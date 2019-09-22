package cerulean.hw1.Api.Controllers;

import cerulean.hw1.Api.Models.Account.Account;
import cerulean.hw1.Database.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value ="/register", method = RequestMethod.POST)
    public void register(@RequestBody String username, String password) {
        //TODO: check if account exists
        Account newAccount = new Account(username, password);
        accountRepository.save(newAccount);
    }
    @RequestMapping(value ="/{loginSession}", method = RequestMethod.POST)
    public String getAccount( @RequestBody String username, String password) {
        return accountRepository.get(username, password);
    }
}
