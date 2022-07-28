package Gdsc.web.post.controller;

import Gdsc.web.common.dto.ApiResponse;
import Gdsc.web.post.dto.PostRequestDto;
import Gdsc.web.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@Controller
public class PostApiController {
    private final PostService postService;



    @PostMapping("/api/member/v2/post")
    public ApiResponse saveJsonPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal User principal) throws IOException {

        postService.save(postRequestDto , principal.getUsername());
        return ApiResponse.success("message", "SUCCESS");
    }


    // 삭제

    @DeleteMapping("/api/member/v2/post/{postId}")
    public ApiResponse deletePost(@PathVariable Long postId ,@AuthenticationPrincipal User principal){
        postService.deletePost(postId , principal.getUsername());
        return ApiResponse.success("message" , "SUCCESS");
    }

    @PutMapping("/api/member/v2/post/{postId}")
    public ApiResponse updateJsonPost(@PathVariable Long postId,
                                      @RequestBody PostRequestDto requestDto ,
                                      @AuthenticationPrincipal User principal) throws IOException {
        postService.update(requestDto, postId , principal.getUsername());

        return ApiResponse.success("message","SUCCESS");
    }


    //조회

    @GetMapping("/api/v1/post/{postId}")
    public ApiResponse findByPostId(@PathVariable Long postId, HttpServletRequest request, HttpServletResponse response){
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("postView")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + postId.toString() + "]")) {
                postService.updateView(postId);
                oldCookie.setValue(oldCookie.getValue() + "_[" + postId + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            postService.updateView(postId);
            Cookie newCookie = new Cookie("postView","[" + postId + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }
        return ApiResponse.success("data",postService.findByPostIdAndBlockIsFalse(postId));
    }



    @GetMapping("/api/v1/post/list")
    public ApiResponse findPostAll( @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC ) Pageable pageable){
        Page<?> post = postService.findPostAll(pageable);
        return ApiResponse.success("data", post);
    }

    @GetMapping("/api/v1/post/list/{categoryName}")
    public ApiResponse findPostAllWithCategory(@PathVariable String categoryName, @PageableDefault
            (size = 16, sort = "postId", direction = Sort.Direction.DESC) Pageable pageable){
        Page<?> post = postService.findPostAllWithCategory(categoryName, pageable);
        return ApiResponse.success("data", post);
    }

    @GetMapping("/api/v1/post/search/{word}")
    public ApiResponse findPostSearch(@PathVariable String word,@PageableDefault
            (size = 16, sort = "postId", direction = Sort.Direction.DESC) Pageable pageable){
        Page<?> post = postService.findFullTextSearch(word,pageable);
        return ApiResponse.success("data", post);
    }



    

}
