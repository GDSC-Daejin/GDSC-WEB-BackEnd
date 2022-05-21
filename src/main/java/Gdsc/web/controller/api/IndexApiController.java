package Gdsc.web.controller.api;

import Gdsc.web.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableScheduling
public class IndexApiController {
    @Autowired
    private PostService postService;
    @PostMapping("/reindex")
    // 1시간 마다 아래 실행!
    @Scheduled(fixedDelay = 3600000, initialDelay = 1000)
    public void reindex() throws InterruptedException {
        postService.initialIndexing();
    }
}
