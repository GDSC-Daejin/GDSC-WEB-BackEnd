package Gdsc.web.controller.api;

import Gdsc.web.dto.ApiResponse;
import Gdsc.web.entity.MemberScrapPost;
import Gdsc.web.entity.Post;
import Gdsc.web.service.ScrapService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScrapApiController {
    private final ScrapService scrapService;

    @ApiOperation(value = "스크랩", notes = "scrap 되어있으면 delete 없으면 scrap")
    @PostMapping("/api/member/v1/scrap/{postId}")
    public ApiResponse scrap(@AuthenticationPrincipal User principal, @PathVariable Long postId){
        scrapService.scrap(principal.getUsername(), postId);
        return ApiResponse.success("message", "SUCCESS");
    }

    @ApiOperation(value = "스크랩한 게시글 불러오기", notes = "내가 스크랩한 게시글 조회")
    @GetMapping("/api/member/v1/myScrap")
    public ApiResponse myScrap(@AuthenticationPrincipal User principal,
                               @PageableDefault(size = 16 ,sort = "id",direction = Sort.Direction.DESC ) Pageable pageable){
        Page<?> scrap = scrapService.findMyScrapPost(principal.getUsername(), pageable);
        return ApiResponse.success("data", scrap);
    }
}
