package Gdsc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {
    @GetMapping("/")
    public String test() {
<<<<<<< Updated upstream
        return "hi hi";
=======
>>>>>>> Stashed changes
        return "hi hi!  ";

    }

}
