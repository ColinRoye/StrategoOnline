package repositories;

import models.Player;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface PlayerRepository extends MongoRepository<Player, String> {
    Player findBy_id(ObjectId _id);
}