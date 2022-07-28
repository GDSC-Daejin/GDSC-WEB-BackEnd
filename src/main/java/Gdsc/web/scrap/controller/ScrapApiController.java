package Gdsc.web.scrap.controller;

import Gdsc.web.common.dto.ApiResponse;
import Gdsc.web.scrap.service.ScrapService;
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

    @PostMapping("/api/member/v1/scrap/{postId}")
    public ApiResponse scrap(@AuthenticationPrincipal User principal, @PathVariable Long postId){
        scrapService.scrap(principal.getUsername(), postId);
        return ApiResponse.success("message", "SUCCESS");
    }

    @GetMapping("/api/member/v1/myScrap")
    public ApiResponse myScrap(@AuthenticationPrincipal User principal,
                               @PageableDefault(size = 16 ,sort = "id",direction = Sort.Direction.DESC ) Pageable pageable){
        Page<?> scrap = scrapService.findMyScrapPost(principal.getUsername(), pageable);
        return ApiResponse.success("data", scrap);
    }
}
