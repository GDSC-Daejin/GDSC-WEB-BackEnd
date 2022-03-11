package Gdsc.web.controller.api;

import Gdsc.web.dto.ApiResponse;
import Gdsc.web.dto.PostRequestDto;
import Gdsc.web.entity.Post;
import Gdsc.web.entity.PostHashTag;
import Gdsc.web.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;

    //등록
    @PostMapping("/api/v1/member/post")
    public ApiResponse save(@ModelAttribute @Valid PostRequestDto requestDto , @AuthenticationPrincipal User principal) throws IOException {
        postService.save(requestDto , principal.getUsername());
        return ApiResponse.success("message", "SUCCESS");
    }

    //수정
    @PutMapping("/api/v1/member/post/{id}")
    public ApiResponse update(@PathVariable Long postId,
                              @RequestBody PostRequestDto requestDto ,
                              @AuthenticationPrincipal User principal) throws IOException {

        postService.update(requestDto, postId , principal.getUsername());
        return ApiResponse.success("message","SUCCESS");
    }

    //조회
    @GetMapping("/api/v1/post/{PostId}")
    public ApiResponse findByPostId(@PathVariable Long PostId){
        return ApiResponse.success("data",postService.findByPostId(PostId));
    }

}
