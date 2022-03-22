package Gdsc.web.controller.api;

import Gdsc.web.dto.ApiResponse;
import Gdsc.web.dto.ResponseDto;
import Gdsc.web.entity.Member;
import Gdsc.web.entity.MemberInfo;
import Gdsc.web.entity.MemberScrapPost;
import Gdsc.web.entity.Post;
import Gdsc.web.repository.member.JpaMemberRepository;
import Gdsc.web.service.MemberService;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import Gdsc.web.service.PostService;
import Gdsc.web.service.ScrapService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

   /* @ApiOperation(value = "관리자 권한 멤버 리스트 확인 // 수정 필요", notes = "온보딩 , 멤버 데이터 전체 봄")
    @GetMapping("/api/core/memberList")
    public ResponseDto<List<Member>> memberList() {
        return new ResponseDto<>(HttpStatus.OK, memberService.멤버리스트(), "성공");
    }*/

    @GetMapping("/user/me")
    public Member getUser(@AuthenticationPrincipal User principal) {
        Member member =memberService.getUserId(principal.getUsername());
        return member;
    }


    @ApiOperation(value = "Member 내용 보기" , notes = "Member 내용 값 보기")
    @GetMapping("/api/guest/v1/me")
    public ApiResponse getUserV2(@AuthenticationPrincipal User principal) {

        Member member =memberService.getUserId(principal.getUsername());
        return ApiResponse.success("data" , member);
    }

    @ApiOperation(value = "Member 내용 보기" , notes = "Member 내용 값 보기")
    @GetMapping("/api/guest/v1/info")
    public ApiResponse getMemberInfo(@AuthenticationPrincipal User principal) {

        MemberInfo memberInfo = memberService.getUserId(principal.getUsername()).getMemberInfo();
        return ApiResponse.success("data" , memberInfo);
    }

    @ApiOperation(value = "유저 자기 정보 업데이트" , notes = "JWT 토큰값이 들어가야 사용자를 인식 가능함")
    @PutMapping("/api/guest/v1/me")
    public ApiResponse Update(@AuthenticationPrincipal User principal , @RequestBody MemberInfo memberInfo){
        if(principal == null) return ApiResponse.fail("message" , "Token is null");
        memberService.정보업데이트(principal.getUsername(),memberInfo);
        return ApiResponse.success("message" , "SUCCESS");
    }

    @ApiOperation(value = "닉네임 중복검사" , notes = "nickname 보낸 값이 중복인지 검사 있으면 false return 없으면 true return")
    @PostMapping("/api/guest/v1/validation/nickname")
    public ApiResponse validationNickname(@RequestBody String nickname){
        // 있으면 false return 없으면 true return
        return ApiResponse.success("data" ,!memberService.닉네임중복검사(nickname));
    }




}