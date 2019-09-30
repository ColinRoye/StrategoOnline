
package cerulean.hw1.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    @RequestMapping("/register")
    public String register() {
        return "register";
    }
    @RequestMapping("/play")
    public String play() {
        return "play";
    }
    @RequestMapping("/game")
    public String games() {
        return "game";
    }
    @RequestMapping("/games")
    public String previousGames() {
        return "games";
    }
}