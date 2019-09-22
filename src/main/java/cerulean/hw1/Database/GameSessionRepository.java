package cerulean.hw1.Database;
import cerulean.hw1.Api.Models.Account.Account;
import cerulean.hw1.Api.Models.Sessions.GameSession;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSessionRepository extends MongoRepository<Account, String> {
    public String get(String gameSessionId);
    public void save(String gameSessionJson);
    public void remove(String gameSessionId);
}
