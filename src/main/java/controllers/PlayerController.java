package controllers;
import models.Player;
import repositories.PlayerRepository;

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
        System.out.println("test");
        return repository.findBy_id(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Player createPlayer(@Valid @RequestBody Player player) {
        player.set_id(ObjectId.get());
        repository.save(player);
        System.out.println("test2");
        return player;
    }
}