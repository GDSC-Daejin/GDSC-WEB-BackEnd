package Gdsc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class SwaggerController {
    @GetMapping("/swagger/api/description")
    public String ApiDescription() {
        return "redirect:/swagger-ui.html";
    }

}
