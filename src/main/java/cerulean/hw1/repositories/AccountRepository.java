package cerulean.hw1.repositories;

import cerulean.hw1.models.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
    Account findByUsername(String username);
}
