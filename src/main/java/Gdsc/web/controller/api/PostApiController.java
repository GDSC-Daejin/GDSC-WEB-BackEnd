package Gdsc.web.controller.api;

import Gdsc.web.repository.post.JpaPostRepository;
import Gdsc.web.repository.post.PostRepositoryImp;
import Gdsc.web.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    
}
