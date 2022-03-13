package Gdsc.web.controller.api;

import Gdsc.web.dto.ApiResponse;
import Gdsc.web.dto.PostRequestDto;
import Gdsc.web.service.LikeService;
import Gdsc.web.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;
    private final LikeService likeService;

    //등록
    @PostMapping("/api/v1/member/post")
    public ApiResponse save(@ModelAttribute @Valid PostRequestDto requestDto , @AuthenticationPrincipal User principal) throws IOException {
        postService.save(requestDto , principal.getUsername());
        return ApiResponse.success("message", "SUCCESS");
    }

    //수정
    @PutMapping("/api/v1/member/post/{id}")
    public ApiResponse update(@PathVariable Long postId,
                              @ModelAttribute @Valid PostRequestDto requestDto ,
                              @AuthenticationPrincipal User principal) throws IOException {

        postService.update(requestDto, postId , principal.getUsername());
        return ApiResponse.success("message","SUCCESS");
    }

    //조회
    @GetMapping("/api/v1/post/{postId}")
    public ApiResponse findByPostId(@PathVariable Long postId){
        return ApiResponse.success("data",postService.findByPostId(postId));
    }

    @PostMapping("/api/v1/post/{postId}/like")
    public ApiResponse like(@AuthenticationPrincipal User principal , @PathVariable Long postId){
        likeService.like(principal.getUsername(), postId);
        return ApiResponse.success("message","SUCCESS");
    }

}
