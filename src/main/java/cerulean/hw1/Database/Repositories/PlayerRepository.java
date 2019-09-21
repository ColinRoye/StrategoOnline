package cerulean.hw1.Database.Repositories;

import cerulean.hw1.Api.Models.Player;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {
    Player findBy_id(ObjectId _id);
}