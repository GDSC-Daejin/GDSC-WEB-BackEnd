package Gdsc.web.post.controller;

import Gdsc.web.common.dto.ApiResponse;
import Gdsc.web.post.dto.PostRequestDto;
import Gdsc.web.post.service.PostService;
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


    @ApiOperation(value = "포스트 글쓰기", notes = "Json base64인코딩 한 내용!")
    @PostMapping("/api/member/v2/post")
    public ApiResponse saveJsonPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal User principal) throws IOException {

        postService.save(postRequestDto , principal.getUsername());
        return ApiResponse.success("message", "SUCCESS");
    }


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
            notes = """
                    PostId로 상세보기\s
                    api 주소에 PathVariable 주면 됩니다.
                    임시글, 블록된 글 안보임!""")
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

    @ApiOperation(value = "제목 , 내용 , 해쉬태그로 검색 기능",
            notes = "Full text search 기능 아직 한국어 미지원\n" +
                    "api/v1/post/search/{word}\n" +
                    "= 검색어어어 근데 Null 또는 공백이면 오류 남! 기본 검색어 넣어주는게 좋을 것 같음")
    @GetMapping("/api/v1/post/search/{word}")
    public ApiResponse findPostSearch(@PathVariable String word,@PageableDefault
            (size = 16, sort = "postId", direction = Sort.Direction.DESC) Pageable pageable){
        Page<?> post = postService.findFullTextSearch(word,pageable);
        return ApiResponse.success("data", post);
    }



    

}
