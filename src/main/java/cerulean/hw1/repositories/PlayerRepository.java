package cerulean.hw1.repositories;

import cerulean.hw1.models.Player;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {
    Player findBy_id(ObjectId _id);
}