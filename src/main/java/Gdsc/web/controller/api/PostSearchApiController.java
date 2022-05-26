package Gdsc.web.controller.api;

import Gdsc.web.dto.ApiResponse;
import Gdsc.web.entity.Post;
import Gdsc.web.service.PostSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostSearchApiController {
    private final PostSearchService postSearchService;
    @GetMapping("/api/v1/post/search/{word}")
    public ApiResponse search(@PathVariable String word, Pageable pageable) {
        Page<Post> post = postSearchService.searchSimilar(word, pageable);
        return ApiResponse.success("data",post);
    }
}
