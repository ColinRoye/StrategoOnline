package cerulean.hw1.controllers;
import cerulean.hw1.models.Player;
import cerulean.hw1.repositories.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
    public void modifyPlayerById(@PathVariable("id") String id, @Valid @RequestBody Player player) {
        player.set_id(new ObjectId(id));
        repository.save(player);
        //return player;
    }

}