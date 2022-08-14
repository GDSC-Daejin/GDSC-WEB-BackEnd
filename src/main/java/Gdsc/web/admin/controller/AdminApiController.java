package Gdsc.web.admin.controller;

import Gdsc.web.common.dto.Response;

import Gdsc.web.admin.service.PostBlockService;
import Gdsc.web.post.dto.PostResponseDto;
import Gdsc.web.post.entity.Post;
import Gdsc.web.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "AdminApiController", description = "블로그 관리자 API")
public class AdminApiController {

    private final PostService postService;
    private final PostBlockService postBlockService;
    @Operation(summary = "블로그 관리자 포스트 블락", description = "블로그 관리자 포스트 막기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "400", description = "요청 오류"),
            @ApiResponse(responseCode = "401", description = "인가 실패"),
            @ApiResponse(responseCode = "403", description = "인가 실패"),
            @ApiResponse(responseCode = "404", description = "요청 오류"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("v1/block/{postId}")
    public Response blockPost(@PathVariable Long postId){
        postBlockService.block(postId);
        return Response.success("message", "Success");
    }

    @Operation(summary = "블로그 관리자 포스트 리스트", description = "블로그 관리자 포스트 리스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PostResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "요청 오류"),
            @ApiResponse(responseCode = "401", description = "인가 실패"),
            @ApiResponse(responseCode = "403", description = "인가 실패"),
            @ApiResponse(responseCode = "404", description = "요청 오류"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("v1/block")
    public Response blockPost(@PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC) Pageable pageable){
        Page<?> post = postService.findBockedPostAll(pageable);
        return Response.success("data", post);
    }
}
