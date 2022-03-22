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
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;


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


    @ApiOperation(value = "post 글 목록 불러오기",
            notes = "글 목록 불러오기 ex : api/v1/post/list?page=0&size=5&sort=postId.desc" +
                    "page : 몇번째 page 불러올건지 " +
                    "size : 1페이지 당 개수" +
                    "sort : 어떤것을 기준으로 정렬 할 것 인지" +
                    "default : Size  16 , sort postId" +
                    "임시저장글 X")
    @GetMapping("/api/v1/post/list")
    public ApiResponse findPostAll( @PageableDefault(size = 16 ,sort = "id",direction = Sort.Direction.DESC ) Pageable pageable){
        Page<Post> post = postService.findPostAll(pageable);
        return ApiResponse.success("data", post);
    }

    @ApiOperation(value = "카테고리 별 글 목록 불러오기", notes = "카테고리 별 모든 게시글을 조회합니다.")
    @GetMapping("/api/v1/post/list/{categoryName}")
    public ApiResponse findPostAllWithCategory(@PathVariable String categoryName, @PageableDefault
            (size = 16, sort = "postId", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Post> post = postService.findPostAllWithCategory(categoryName, pageable);
        return ApiResponse.success("data", post);
    }

    @ApiOperation(value ="작성 게시글 불러오기", notes = "내가 작성한 게시글을 조회")
    @GetMapping("/api/member/v1/myPost")
    public ApiResponse myPost(@AuthenticationPrincipal User principal,
                              @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC) Pageable pageable){
        Page<Post> post = postService.findMyPost(principal.getUsername(), pageable);
        return ApiResponse.success("data", post);
    }

    @ApiOperation(value ="카테고리별 작성 게시글 불러오기", notes = "내가 작성한 게시글을 카테고리 별로 조회")
    @GetMapping("api/member/v1/myPost/{categoryName}")
    public ApiResponse myPostWithCategory(@AuthenticationPrincipal User principal,
                                          @PathVariable String categoryName,
                                          @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC)Pageable pageable){
        Page<Post> post = postService.findMyPostWIthCategory(principal.getUsername(), categoryName, pageable);
        return ApiResponse.success("data", post);
    }
}
