package Gdsc.web.post.controller;

import Gdsc.web.common.dto.ApiResponse;
import Gdsc.web.post.service.MyPostService;
import Gdsc.web.post.service.PostService;


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

    @GetMapping("/api/member/v1/myPost")
    public ApiResponse myPost(@AuthenticationPrincipal User principal,
                              @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC) Pageable pageable){
        Page<?> post = postService.findAllMyPost(principal.getUsername(), pageable);
        return ApiResponse.success("data", post);
    }

    @GetMapping("/api/member/v1/myPost/{postId}")
    public ApiResponse myPostTemp(@AuthenticationPrincipal User principal,
                                  @PathVariable Long postId){
        return ApiResponse.success("data",  postService.findMyPost(principal.getUsername(), postId));
    }


    @GetMapping("/api/member/v1/myPost/temp")
    public ApiResponse myPostTemp(@AuthenticationPrincipal User principal,
                                  @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC)Pageable pageable){
        Page<?> post = postService.findAllMyTmpPosts(principal.getUsername(), pageable);
        return ApiResponse.success("data", post);
    }
    @GetMapping("/api/member/v1/myPost/notTemp")
    public ApiResponse myPostNotTemp(@AuthenticationPrincipal User principal,
                                  @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC)Pageable pageable){
        Page<?> post = postService.findAllMyNotTmpPosts(principal.getUsername(), pageable);
        return ApiResponse.success("data", post);
    }
    @GetMapping("/api/member/v1/myPost/notTemp/{categoryName}")
    public ApiResponse myPostNotTempWithCategory(@AuthenticationPrincipal User principal,
                                                 @PathVariable String categoryName,
                                                 @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC)Pageable pageable){
        Page<?> post = postService.findAllMyNotTmpPostsWithCategory(principal.getUsername() , categoryName , pageable);
        return ApiResponse.success("data" , post);

    }
    @GetMapping("/api/member/v1/myPost/temp/{categoryName}")
    public ApiResponse myPostTempWithCategory(@AuthenticationPrincipal User principal,
                                              @PathVariable String categoryName,
                                              @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC)Pageable pageable){
        Page<?> post = postService.findAllMyTmpPostWithCategory(principal.getUsername(), categoryName,pageable);
        return ApiResponse.success("data", post);
    }

    @GetMapping("api/member/v1/myPost/category/{categoryName}")
    public ApiResponse myPostWithCategory(@AuthenticationPrincipal User principal,
                                          @PathVariable String categoryName,
                                          @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC)Pageable pageable){
        Page<?> post = postService.findAllMyPostWIthCategory(principal.getUsername(), categoryName, pageable);
        return ApiResponse.success("data", post);
    }
}
