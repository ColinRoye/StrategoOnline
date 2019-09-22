package cerulean.hw1.Database;
import cerulean.hw1.Api.Models.Account.Account;
import cerulean.hw1.Api.Models.Sessions.LoginSession;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginSessionRepository extends MongoRepository<Account, String> {
    public boolean exists(String username, String password);
    public void save(String loginSessionJson);
    public void remove(String loginSessionId);
}
