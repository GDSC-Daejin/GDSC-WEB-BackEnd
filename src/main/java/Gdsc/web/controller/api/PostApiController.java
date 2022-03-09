package Gdsc.web.controller.api;

import Gdsc.web.dto.PostResponseDto;
import Gdsc.web.dto.PostSaveRequestDto;
import Gdsc.web.dto.PostUpdateRequestDto;
import Gdsc.web.repository.post.JpaPostRepository;
import Gdsc.web.repository.post.PostRepositoryImp;
import Gdsc.web.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;

    //등록
    @PostMapping("/api/v1/post")
    public Long save(@RequestBody PostSaveRequestDto requestDto){
        return postService.save(requestDto);
    }

    //수정
    @PutMapping("/api/v1/post/{id}")
    public Long update(@PathVariable Long postId, @RequestBody PostUpdateRequestDto requestDto){
        return postService.update(postId, requestDto);
    }

    //조회
    @GetMapping("/api/v1/post/{id}")
    public PostResponseDto findById(@PathVariable Long postId){
        return postService.findById(postId);
    }

}
