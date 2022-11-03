package Gdsc.web.post.controller;

import Gdsc.web.common.dto.Response;
import Gdsc.web.member.service.MemberService;
import Gdsc.web.post.dto.PostResponseDto;
import Gdsc.web.post.entity.Post;
import Gdsc.web.post.mapper.PostMapper;
import Gdsc.web.post.service.OtherPostService;
import Gdsc.web.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "다른사람 My Blog 에서 쓴 글 api", description = "내가 쓴 글 api")
public class OtherPostApiController {
    private final OtherPostService postService;
    private final MemberService memberService;
    private final PostMapper postMapper;
    @Operation(summary = "다른사람 MyBlog 임시X 글리스트 불러오기", description = "다른사람 MyBlog  임시X 글리스트 불러오기")
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
    @GetMapping("/api/v1/{userId}/notTemp")
    public Response myPostNotTemp(@PathVariable String userId,
                                  @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC) Pageable pageable){
        List<Post> postList = postService.findAllNotTmpPosts(userId, pageable);
        Page<PostResponseDto> postResponseDtoPage = postMapper.toPostResponseDtoPage(postList,memberService, pageable);
        return Response.success("data", postResponseDtoPage);
    }
    @Operation(summary = "다른사람 MyBlog  글 카테고리별 리스트 불러오기", description = "다른사람 MyBlog 임시X 글 카테고리 별 리스트 불러오기")
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
    @GetMapping("/api/v1/{userId}/notTemp/{categoryName}")
    public Response myPostNotTempWithCategory(@PathVariable String userId,
                                              @PathVariable String categoryName,
                                              @PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC)Pageable pageable){
        List<Post> post = postService.findAllNotTmpPostsWithCategory(userId, categoryName , pageable);
        Page<PostResponseDto> postResponseDtoPage = postMapper.toPostResponseDtoPage(post,memberService, pageable);
        return Response.success("data" , postResponseDtoPage);

    }
}
