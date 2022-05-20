package Gdsc.web.controller.api;

import Gdsc.web.dto.ApiResponse;
import Gdsc.web.dto.requestDto.PostRequestDto;
import Gdsc.web.dto.requestDto.PostResponseDto;
import Gdsc.web.entity.Post;
import Gdsc.web.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@Controller
public class PostApiController {
    private final PostService postService;


    //등록
    /*@ApiOperation(value = "포스트 글쓰기", notes = "Json 아니고 form type으로 보내야함")
    @PostMapping("/api/member/v1/post")
    public ApiResponse saveFormData(@ModelAttribute @Valid PostRequestDto requestDto  , @AuthenticationPrincipal User principal) throws IOException {
        postService.save(requestDto , principal.getUsername());
        return ApiResponse.success("message", "SUCCESS");
    }*/
    @ApiOperation(value = "포스트 글쓰기", notes = "Json base64인코딩 한 내용!")
    @PostMapping("/api/member/v2/post")
    public ApiResponse saveJsonPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal User principal) throws IOException {

        postService.save(postRequestDto , principal.getUsername());
        return ApiResponse.success("message", "SUCCESS");
    }
    //수정
    /*@ApiOperation(value = "post 업데이트", notes = "JWT 토큰으로 user정보 읽고 변한값 Json 아니고 form type으로 보내야함")
    @PutMapping("/api/member/v1/post/{postId}")
    public ApiResponse updateFormData(@PathVariable Long postId,
                              @ModelAttribute @Valid PostRequestDto requestDto ,
                              @AuthenticationPrincipal User principal) throws IOException {

        postService.update(requestDto, postId , principal.getUsername());
        return ApiResponse.success("message","SUCCESS");
    }*/

    // 삭제
    @ApiOperation(value = "post 삭제" , notes = "현재 로그인한 사람과 일치해야 삭제 가능")
    @DeleteMapping("/api/member/v2/post/{postId}")
    public ApiResponse deletePost(@PathVariable Long postId ,@AuthenticationPrincipal User principal){
        postService.deletePost(postId , principal.getUsername());
        return ApiResponse.success("message" , "SUCCESS");
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
    @ApiOperation(value = "post 상세보기",
            notes = "PostId로 상세보기\n" +
                    "api 주소에 PathVariable 주면 됩니다.")
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


    @ApiOperation(value = "post 글 목록 불러오기",
            notes = "글 목록 불러오기 ex : api/v1/post/list?page=0&size=5&sort=postId.desc\n" +
                    "page : 몇번째 page 불러올건지\n" +
                    "size : 1페이지 당 개수\n" +
                    "sort : 어떤것을 기준으로 정렬 할 것 인지\n" +
                    "default : Size  16 , sort postId\n" +
                    "임시저장글은 불러오지 않음\n")
    @GetMapping("/api/v1/post/list")
    public ApiResponse findPostAll( @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC ) Pageable pageable){
        Page<?> post = postService.findPostAll(pageable);
        return ApiResponse.success("data", post);
    }

    @ApiOperation(value = "카테고리 별 글 목록 불러오기", notes = "카테고리 별 모든 게시글을 조회합니다.")
    @GetMapping("/api/v1/post/list/{categoryName}")
    public ApiResponse findPostAllWithCategory(@PathVariable String categoryName, @PageableDefault
            (size = 16, sort = "postId", direction = Sort.Direction.DESC) Pageable pageable){
        Page<?> post = postService.findPostAllWithCategory(categoryName, pageable);
        return ApiResponse.success("data", post);
    }
    @ApiOperation(value = "해시태그 별 글 목록 불러오기", notes = "해시태그 별 모든 게시글을 조회합니다.")
    @GetMapping("/api/v1/post/search/{word}")
    public ApiResponse findPostAllWithPostHashTag(@PathVariable String word, @PageableDefault
            (size = 16, sort = "postId", direction = Sort.Direction.DESC) Pageable pageable){
        Page<?> post = postService.findPostAllWithPostHashTag(word, pageable);
        return ApiResponse.success("data", post);
    }
    /*@ApiOperation(value = "제목 검색", notes = "해시태그 별 모든 게시글을 조회합니다.")
    @GetMapping("/api/v1/post/search/title/{title}")
    public ApiResponse findPostAllWithTitle(@PathVariable String title, @PageableDefault
            (size = 16, sort = "postId", direction = Sort.Direction.DESC) Pageable pageable){
        Page<?> post = postService.findPostAllByTitle(title, pageable);
        return ApiResponse.success("data", post);
    }*/
    @GetMapping("/api/v1/post/search/title/{title}")
    public ApiResponse findPostAllWithTitle(@PathVariable String title)  {

        List<?> post = postService.findFullTextSearch(title,10,10);
        return ApiResponse.success("data", post);
    }
    @ApiOperation(value ="내가 작성한 게시글 불러오기", notes = "내가 작성한 게시글을 조회")
    @GetMapping("/api/member/v1/myPost")
    public ApiResponse myPost(@AuthenticationPrincipal User principal,
                              @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC) Pageable pageable){
        Page<?> post = postService.findMyPost(principal.getUsername(), pageable);
        return ApiResponse.success("data", post);
    }

    @ApiOperation(value ="카테고리별 작성 게시글 불러오기", notes = "내가 작성한 게시글을 카테고리 별로 조회")
    @GetMapping("api/member/v1/myPost/{categoryName}")
    public ApiResponse myPostWithCategory(@AuthenticationPrincipal User principal,
                                          @PathVariable String categoryName,
                                          @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC)Pageable pageable){
        Page<?> post = postService.findMyPostWIthCategory(principal.getUsername(), categoryName, pageable);
        return ApiResponse.success("data", post);
    }
    @ApiOperation(value = "내 임시 저장글 전부 불러오기", notes = "임시 저장글을 불러옵니다.")
    @GetMapping("/api/member/v1/myPost/temp")
    public ApiResponse myPostTemp(@AuthenticationPrincipal User principal,
                                  @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC)Pageable pageable){
        Page<?> post = postService.findAllMyTmpPost(principal.getUsername(), pageable);
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
    @ApiOperation(value = "내 임시 저장글 상세보기", notes = "임시 저장글을 불러옵니다.")
    @GetMapping("/api/member/v1/myPost/temp/post/{postId}")
    public ApiResponse myPostTemp(@AuthenticationPrincipal User principal,
                                  @PathVariable Long postId){
        return ApiResponse.success("data",  postService.findMyTmpPost(principal.getUsername(), postId));
    }

    

}
