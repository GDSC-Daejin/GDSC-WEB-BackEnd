package Gdsc.web.scrap.controller;

import Gdsc.web.common.dto.Response;
import Gdsc.web.member.service.MemberService;
import Gdsc.web.post.dto.PostResponseDto;
import Gdsc.web.post.entity.Post;
import Gdsc.web.post.mapper.PostMapper;
import Gdsc.web.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ScrapApiController {
    private final ScrapService scrapService;
    private final MemberService memberService;
    private final PostMapper postMapper;

    @PostMapping("/api/guest/v1/scrap/{postId}")
    public Response scrap(@AuthenticationPrincipal User principal, @PathVariable Long postId) {
        scrapService.scrap(principal.getUsername(), postId);
        return Response.success("message", "SUCCESS");
    }

    @GetMapping("/api/guest/v1/myScrap")
    public Response myScrap(@AuthenticationPrincipal User principal,
                            @PageableDefault(size = 16, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        List<Post> post = scrapService.findMyScrapPost(principal.getUsername(), pageable);

        return Response.success("data", postMapper.toPostResponseDtoPage(post,memberService,pageable));
    }

    @GetMapping("/api/guest/v1/myScrap/{category}")
    public Response myScrapByCategory(@AuthenticationPrincipal User principal,
                                      @PathVariable String category,
                                      @PageableDefault(size = 16, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<Post> scrap = scrapService.findMyScrapPostByCategory(principal.getUsername(), category, pageable);
        Page<PostResponseDto> postResponseDtoPage = postMapper.toPostResponseDtoPage(scrap, memberService, pageable);
        return Response.success("data", postResponseDtoPage);
    }

    @GetMapping("/api/guest/v1/myScrap/list")
    public Response myScrapList(@AuthenticationPrincipal User principal) {
        List<Long> scrap = scrapService.findMyScrapPostList(principal.getUsername());
        return Response.success("data", scrap);
    }
}
