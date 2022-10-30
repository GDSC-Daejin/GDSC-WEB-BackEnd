package Gdsc.web.post.controller;

import Gdsc.web.common.dto.Response;
import Gdsc.web.member.service.MemberService;
import Gdsc.web.post.dto.PostResponseDto;
import Gdsc.web.post.entity.Post;
import Gdsc.web.post.mapper.PostMapper;
import Gdsc.web.post.service.MyPostService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "내가 쓴 글 api", description = "내가 쓴 글 api")
public class MyPostApiController {
    private final MyPostService postService;
    private final MemberService memberService;
    private final PostMapper postMapper;
    @Operation(summary = "내가 쓴 글리스트 불러오기", description = "내가 쓴 글리스트 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PostResponseDto.class)))),
            @ApiResponse(responseCode = "401", description = "로그인 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시물"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/api/guest/v1/myPost")
    public Response myPost(@AuthenticationPrincipal User principal,
                           @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC) Pageable pageable){
        List<Post> posts = postService.findAllMyPost(principal.getUsername(), pageable);
        Page<PostResponseDto> postResponseDtoPage = postMapper.toPostResponseDtoPage(posts,memberService, pageable);
        return Response.success("data", postResponseDtoPage);
    }
    @Operation(summary = "내가 쓴 글 불러오기", description = "내가 쓴 글 불러오기")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = PostResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "로그인 필요"),
                    @ApiResponse(responseCode = "403", description = "권한 없음"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 게시물"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    @GetMapping("/api/guest/v1/myPost/{postId}")
    public Response myPostTemp(@AuthenticationPrincipal User principal,
                               @PathVariable Long postId){
        Post post = postService.findMyPost(principal.getUsername(), postId);
        PostResponseDto postResponseDto = postMapper.toPostResponseDto(post,memberService);
        return Response.success("data", postResponseDto);
    }
    @Operation(summary = "내가  임시글 리스트 불러오기", description = "내가 쓴 임시글")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PostResponseDto.class)))),
                    @ApiResponse(responseCode = "401", description = "로그인 필요"),
                    @ApiResponse(responseCode = "403", description = "권한 없음"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 게시물"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    @GetMapping("/api/guest/v1/myPost/temp")
    public Response myPostTemp(@AuthenticationPrincipal User principal,
                               @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC)Pageable pageable){
        List<Post> post = postService.findAllMyTmpPosts(principal.getUsername(), pageable);
        Page<PostResponseDto> postResponseDtoPage = postMapper.toPostResponseDtoPage(post,memberService, pageable);
        return Response.success("data", postResponseDtoPage);
    }
    @Operation(summary = "내가 임시X 글리스트 불러오기", description = "내가 쓴 임시X 글리스트 불러오기")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PostResponseDto.class)))),
                    @ApiResponse(responseCode = "401", description = "로그인 필요"),
                    @ApiResponse(responseCode = "403", description = "권한 없음"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 게시물"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    @GetMapping("/api/guest/v1/myPost/notTemp")
    public Response myPostNotTemp(@AuthenticationPrincipal User principal,
                                  @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC)Pageable pageable){
        List<Post> post = postService.findAllMyNotTmpPosts(principal.getUsername(), pageable);
        Page<PostResponseDto> postResponseDtoPage = postMapper.toPostResponseDtoPage(post,memberService, pageable);
        return Response.success("data", postResponseDtoPage);
    }
    @Operation(summary = "내가 임시X 글 카테고리별 리스트 불러오기", description = "임시X 글 카테고리 별 리스트 불러오기")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PostResponseDto.class)))),
                    @ApiResponse(responseCode = "401", description = "로그인 필요"),
                    @ApiResponse(responseCode = "403", description = "권한 없음"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 게시물"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    @GetMapping("/api/guest/v1/myPost/notTemp/{categoryName}")
    public Response myPostNotTempWithCategory(@AuthenticationPrincipal User principal,
                                              @PathVariable String categoryName,
                                              @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC)Pageable pageable){
        List<Post> post = postService.findAllMyNotTmpPostsWithCategory(principal.getUsername() , categoryName , pageable);
        Page<PostResponseDto> postResponseDtoPage = postMapper.toPostResponseDtoPage(post,memberService, pageable);
        return Response.success("data" , postResponseDtoPage);

    }
    @Operation(summary = "내가 임시 글 카테고리별 불러오기", description = "임시 글 카테고리 별 불러오기")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PostResponseDto.class)))),
                    @ApiResponse(responseCode = "401", description = "로그인 필요"),
                    @ApiResponse(responseCode = "403", description = "권한 없음"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 게시물"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    @GetMapping("/api/guest/v1/myPost/temp/{categoryName}")
    public Response myPostTempWithCategory(@AuthenticationPrincipal User principal,
                                           @PathVariable String categoryName,
                                           @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC)Pageable pageable){
        List<Post> post = postService.findAllMyTmpPostWithCategory(principal.getUsername(), categoryName,pageable);
        Page<PostResponseDto> postResponseDtoPage = postMapper.toPostResponseDtoPage(post,memberService, pageable);
        return Response.success("data", postResponseDtoPage);
    }
    @Operation(summary = "내가 쓴 글 카테고리별 리스트 불러오기", description = "내가 쓴 글 임시던 아니던 전부 카테고리별로 불러오기")
    @GetMapping("api/guest/v1/myPost/category/{categoryName}")
    public Response myPostWithCategory(@AuthenticationPrincipal User principal,
                                       @PathVariable String categoryName,
                                       @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC)Pageable pageable){
        List<Post> post = postService.findAllMyPostWIthCategory(principal.getUsername(), categoryName, pageable);
        Page<PostResponseDto> postResponseDtoPage = postMapper.toPostResponseDtoPage(post,memberService, pageable);
        return Response.success("data", postResponseDtoPage);
    }
}
