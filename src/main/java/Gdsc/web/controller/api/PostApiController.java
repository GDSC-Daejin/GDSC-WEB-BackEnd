package Gdsc.web.controller.api;

import Gdsc.web.dto.ApiResponse;
import Gdsc.web.dto.PostRequestDto;
import Gdsc.web.service.LikeService;
import Gdsc.web.service.PostService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "포스트 글쓰기", notes = "Json 아니고 form type으로 보내야함")
    @PostMapping("/api/v1/member/post")
    public ApiResponse save(@ModelAttribute @Valid PostRequestDto requestDto  , @AuthenticationPrincipal User principal) throws IOException {
        postService.save(requestDto , principal.getUsername());
        return ApiResponse.success("message", "SUCCESS");
    }

    //수정
    @ApiOperation(value = "post 업데이트", notes = "JWT 토큰으로 user정보 읽고 변한값 Json 아니고 form type으로 보내야함")
    @PutMapping("/api/v1/member/post/{postId}")
    public ApiResponse update(@PathVariable Long postId,
                              @ModelAttribute @Valid PostRequestDto requestDto ,
                              @AuthenticationPrincipal User principal) throws IOException {

        postService.update(requestDto, postId , principal.getUsername());
        return ApiResponse.success("message","SUCCESS");
    }

    //조회
    @ApiOperation(value = "post 상세보기", notes = "Postid로 상세보기")
    @GetMapping("/api/v1/post/{postId}")
    public ApiResponse findByPostId(@PathVariable Long postId){
        return ApiResponse.success("data",postService.findByPostId(postId));
    }
    @ApiOperation(value = "좋아요!", notes = "좋아요 있으면 delete 없으면 없애기")
    @PostMapping("/api/v1/post/{postId}/like")
    public ApiResponse like(@AuthenticationPrincipal User principal , @PathVariable Long postId){
        likeService.like(principal.getUsername(), postId);
        return ApiResponse.success("message","SUCCESS");
    }

}
