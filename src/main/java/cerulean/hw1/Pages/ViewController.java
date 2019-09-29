
package cerulean.hw1.Pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @RequestMapping("/login")
    public String login() {
        return "login"; }
    @RequestMapping("/register")
    public String register() {
        return "register";
    }
    @RequestMapping("/play")
    public String play() {
        return "play";
    }

}