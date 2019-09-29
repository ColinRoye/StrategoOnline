package cerulean.hw1.database;
import cerulean.hw1.models.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {
    Game findByGameId(String gameId);
}
