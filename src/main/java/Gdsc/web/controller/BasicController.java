package Gdsc.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
public class BasicController {
    @GetMapping("/")
    public String test() {
        return "hi hi";
    }
}
