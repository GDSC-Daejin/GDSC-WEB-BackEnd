package Gdsc.web.post.controller;

import Gdsc.web.common.dto.Response;
import Gdsc.web.member.service.MemberService;
import Gdsc.web.post.dto.PostRequestDto;
import Gdsc.web.post.dto.PostResponseDto;
import Gdsc.web.post.entity.Post;
import Gdsc.web.post.mapper.PostMapper;
import Gdsc.web.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@Controller
@Tag(name = "게시물 api controller", description = "게시물 api")
public class PostApiController {
    private final PostService postService;
    private final PostMapper postMapper;
    private final MemberService memberService;


    @Operation(summary = "게시물 작성", description = "게시물을 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "작성 성공"),
            @ApiResponse(responseCode = "400", description = "작성 실패")
    })
    @PostMapping("/api/member/v2/post")
    public Response saveJsonPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal User principal) throws IOException {


        Map<String,Long>  res = new HashMap<>();
        res.put("postId", postService.save(postRequestDto , principal.getUsername()));
        return Response.success("data", res);
    }


    // 삭제
    @Operation(summary = "내가 쓴 게시물 삭제", description = "게시물을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공" ),
            @ApiResponse(responseCode = "400", description = "삭제 실패")
    })
    @DeleteMapping("/api/member/v2/post/{postId}")
    public Response deletePost(@PathVariable Long postId , @AuthenticationPrincipal User principal){
        postService.deletePost(postId , principal.getUsername());
        return Response.success("message" , "SUCCESS");
    }
    @Operation(summary = "내가 쓴 게시물 수정", description = "게시물을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공" ),
            @ApiResponse(responseCode = "400", description = "수정 실패")
    })
    @PutMapping("/api/member/v2/post/{postId}")
    public Response updateJsonPost(@PathVariable Long postId,
                                   @RequestBody PostRequestDto requestDto ,
                                   @AuthenticationPrincipal User principal) throws IOException {
        postService.update(requestDto, postId , principal.getUsername());

        return Response.success("message","SUCCESS");
    }


    //조회
    @Operation(summary = "게시물 조회", description = "게시물을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공" , content = @Content(schema = @Schema(implementation = PostResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "조회 실패")
    })
    @GetMapping("/api/v1/post/{postId}")
    public Response findByPostId(@PathVariable Long postId, HttpServletRequest request, HttpServletResponse response){
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
        Post post = postService.findByPostIdAndBlockIsFalse(postId);
        return Response.success("data", postMapper.toPostResponseDto(post,memberService));
    }

    @Operation(summary = "게시물 조회", description = "게시물을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공" , content = @Content(array = @ArraySchema(schema = @Schema(implementation = PostResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "조회 실패")
    })
    @GetMapping("/api/v1/post/list")
    public Response findPostAll(@PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC ) Pageable pageable){
        List<Post> post = postService.findPostAll(pageable);
        Page<PostResponseDto> postResponseDto = postMapper.toPostResponseDtoPage(post,memberService,pageable);
        return Response.success("data", postResponseDto);
    }
    @Operation(summary = "카테고리별 게시물 조회", description = "카테고리별 게시물을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공" , content = @Content(array = @ArraySchema(schema = @Schema(implementation = PostResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "조회 실패")
    })
    @GetMapping("/api/v1/post/list/{categoryName}")
    public Response findPostAllWithCategory(@PathVariable String categoryName, @PageableDefault
            (size = 16, sort = "postId", direction = Sort.Direction.DESC) Pageable pageable){
        List<Post> post = postService.findPostAllWithCategory(categoryName, pageable);
        Page<PostResponseDto> postResponseDtoPage = postMapper.toPostResponseDtoPage(post,memberService, pageable);
        return Response.success("data", postResponseDtoPage);
    }
    @Operation(summary ="게시물 검색" , description = "게시물을 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공" , content = @Content(array = @ArraySchema(schema = @Schema(implementation = PostResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "조회 실패")
    })
    @GetMapping("/api/v1/post/search/{word}")
    public Response findPostSearch(@PathVariable String word, @PageableDefault
            (size = 16, sort = "postId", direction = Sort.Direction.DESC) Pageable pageable){
        List<Post> post = postService.findFullTextSearch(word,pageable);
        Page<PostResponseDto> postResponseDtoPage = postMapper.toPostResponseDtoPage(post,memberService, pageable);
        return Response.success("data", postResponseDtoPage);
    }
    @Operation(summary ="게시물 검색" , description = "게시물을 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공" , content = @Content(array = @ArraySchema(schema = @Schema(implementation = PostResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "조회 실패")
    })
    @GetMapping("/api/v1/post/search/{categoryName}/{word}")
    public Response findPostSearchWithCategory(@PathVariable String word,
                                               @PathVariable String categoryName,
                                               @PageableDefault(size = 16, sort = "postId", direction = Sort.Direction.DESC) Pageable pageable){
        List<Post> post = postService.findFullTextSearchWithCategory(word,categoryName,pageable);
        Page<PostResponseDto> postResponseDtoPage = postMapper.toPostResponseDtoPage(post,memberService, pageable);
        return Response.success("data", postResponseDtoPage);
    }




    

}
