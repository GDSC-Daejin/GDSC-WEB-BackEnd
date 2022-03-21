package Gdsc.web.controller.api;

import Gdsc.web.dto.ApiResponse;
import Gdsc.web.dto.requestDto.PostRequestDto;
import Gdsc.web.entity.Post;
import Gdsc.web.service.LikeService;
import Gdsc.web.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @PostMapping("/api/member/v1/post")
    public ApiResponse saveFormData(@ModelAttribute @Valid PostRequestDto requestDto  , @AuthenticationPrincipal User principal) throws IOException {
        postService.save(requestDto , principal.getUsername());
        return ApiResponse.success("message", "SUCCESS");
    }
    @ApiOperation(value = "포스트 글쓰기", notes = "Json base64인코딩 한 내용!")
    @PostMapping("/api/member/v2/post")
    public ApiResponse saveJsonPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal User principal) throws IOException {
        postService.save(postRequestDto , principal.getUsername());
        return ApiResponse.success("message", "SUCCESS");
    }
    //수정
    @ApiOperation(value = "post 업데이트", notes = "JWT 토큰으로 user정보 읽고 변한값 Json 아니고 form type으로 보내야함")
    @PutMapping("/api/member/v1/post/{postId}")
    public ApiResponse updateFormData(@PathVariable Long postId,
                              @ModelAttribute @Valid PostRequestDto requestDto ,
                              @AuthenticationPrincipal User principal) throws IOException {

        postService.update(requestDto, postId , principal.getUsername());
        return ApiResponse.success("message","SUCCESS");
    }

    @ApiOperation(value = "post 업데이트", notes = "Json Base 64데이터")
    @PutMapping("/api/member/v2/post/{postId}")
    public ApiResponse updateJsonPost(@PathVariable Long postId,
                                      @RequestBody PostRequestDto requestDto ,
                                      @AuthenticationPrincipal User principal) throws IOException {

        postService.update(requestDto, postId , principal.getUsername());
        return ApiResponse.success("message","SUCCESS");
    }


    //조회
    @ApiOperation(value = "post 상세보기", notes = "PostId로 상세보기")
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

    @ApiOperation(value = "post 글 목록 불러오기", notes = "글 목록 불러오기")
    @GetMapping("/api/post/list")
    public ApiResponse findPostAll(Pageable pageable){
        Page<Post> post = postService.findPostAll(pageable);
        return ApiResponse.success("data", post);
    }

}
