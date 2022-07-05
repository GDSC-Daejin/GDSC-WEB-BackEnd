package Gdsc.web.post.controller;

import Gdsc.web.common.dto.ApiResponse;
import Gdsc.web.post.service.MyPostService;
import Gdsc.web.post.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPostApiController {
    private final MyPostService postService;

    @ApiOperation(value ="내가 작성한 게시글 불러오기", notes = "내가 작성한 게시글을 조회 임시저장글 포함하기")
    @GetMapping("/api/member/v1/myPost")
    public ApiResponse myPost(@AuthenticationPrincipal User principal,
                              @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC) Pageable pageable){
        Page<?> post = postService.findAllMyPost(principal.getUsername(), pageable);
        return ApiResponse.success("data", post);
    }

    @ApiOperation(value = "내 글 상세보기", notes = "내가 쓴 글을 불러옵니다. 임시저장글 포함 ")
    @GetMapping("/api/member/v1/myPost/{postId}")
    public ApiResponse myPostTemp(@AuthenticationPrincipal User principal,
                                  @PathVariable Long postId){
        return ApiResponse.success("data",  postService.findMyPost(principal.getUsername(), postId));
    }


    @ApiOperation(value = "내 임시 저장글 전부 불러오기", notes = "임시 저장글을 불러옵니다.")
    @GetMapping("/api/member/v1/myPost/temp")
    public ApiResponse myPostTemp(@AuthenticationPrincipal User principal,
                                  @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC)Pageable pageable){
        Page<?> post = postService.findAllMyTmpPosts(principal.getUsername(), pageable);
        return ApiResponse.success("data", post);
    }
    @ApiOperation(value = "나의의 임시저장 아닌 글 록 불러오기", notes = "임시저장 아닌 글 목록 불러오기")
    @GetMapping("/api/member/v1/myPost/notTemp")
    public ApiResponse myPostNotTemp(@AuthenticationPrincipal User principal,
                                  @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC)Pageable pageable){
        Page<?> post = postService.findAllMyNotTmpPosts(principal.getUsername(), pageable);
        return ApiResponse.success("data", post);
    }
    @ApiOperation(value = "내 임시 저장글 카테고리별 불러오기", notes = "임시 저장글을 카테고리 별로 불러옵니다.")
    @GetMapping("/api/member/v1/myPost/temp/{categoryName}")
    public ApiResponse myPostTempWithCategory(@AuthenticationPrincipal User principal,
                                              @PathVariable String categoryName,
                                              @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC)Pageable pageable){
        Page<?> post = postService.findAllMyTmpPostWithCategory(principal.getUsername(), categoryName,pageable);
        return ApiResponse.success("data", post);
    }

    @ApiOperation(value ="카테고리별 작성 게시글 불러오기", notes = "내가 작성한 게시글을 카테고리 별로 조회")
    @GetMapping("api/member/v1/myPost/category/{categoryName}")
    public ApiResponse myPostWithCategory(@AuthenticationPrincipal User principal,
                                          @PathVariable String categoryName,
                                          @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC)Pageable pageable){
        Page<?> post = postService.findAllMyPostWIthCategory(principal.getUsername(), categoryName, pageable);
        return ApiResponse.success("data", post);
    }
}
