package cerulean.hw1.api;
import cerulean.hw1.models.Player;
import cerulean.hw1.repositories.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.bson.types.ObjectId;

import javax.validation.Valid;
@RestController
@RequestMapping("player")
public class PlayerController {
    @Autowired
    private PlayerRepository repository;

    @RequestMapping(value ="/{id}", method = RequestMethod.GET)
    public Player getPlayerById(@PathVariable("id") ObjectId id) {
        return repository.findBy_id(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void modifyPlayerById(@PathVariable String id) {
       // player.set_id(new ObjectId(id));
        //repository.save(player);
        //return player;
        System.out.println(id);
    }

    @Value("${testingvalue}")
    private String testingvalue;
    @RequestMapping(value="/lamepage", method = RequestMethod.GET)
    public String hello() {
        return "Some dumb page for " + testingvalue;
    }

}
