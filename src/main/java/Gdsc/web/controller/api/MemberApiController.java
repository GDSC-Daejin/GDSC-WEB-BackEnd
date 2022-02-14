package Gdsc.web.controller.api;

import Gdsc.web.controller.dto.ResponseDto;
import Gdsc.web.domain.Member;
import Gdsc.web.domain.MemberNicknameMapping;
import Gdsc.web.repository.MemberRepository;
import Gdsc.web.service.MemberService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {


    private final MemberService memberService;
    private final MemberRepository memberRepository;



    @ApiOperation(value = "관리자 권한 멤버 리스트 확인", notes = "온보딩 , 멤버 데이터 전체 봄")
    @GetMapping("/api/core/memberList")
    public ResponseDto<List<Member>> memberList() {
        return new ResponseDto<>(HttpStatus.OK, memberService.멤버리스트(), "성공");
    }


    @GetMapping("/user/me")
    public Member getUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal.getUsername());
        Member member =memberService.getUserId(principal.getUsername());

        return member;
    }


}
