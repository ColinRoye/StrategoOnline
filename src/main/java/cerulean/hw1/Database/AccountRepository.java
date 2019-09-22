package cerulean.hw1.Database.Repositories;
import cerulean.hw1.Api.Models.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    public boolean exists(String username);
    public void save(String accountJson);
    public String get(String username, String password);
    public void remove(String username, String password);
}
