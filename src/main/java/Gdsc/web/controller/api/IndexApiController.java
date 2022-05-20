package Gdsc.web.controller.api;

import Gdsc.web.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexApiController {
    @Autowired
    private PostService postService;
    @PostMapping("/reindex")
    public void reindex() throws InterruptedException {
        postService.initialIndexing();
    }
}
