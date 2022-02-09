package Gdsc.web.controller.api;

import Gdsc.web.controller.dto.ApiResponse;
import Gdsc.web.controller.dto.ResponseDto;
import Gdsc.web.domain.Member;
import Gdsc.web.domain.OnboardingMember;
import Gdsc.web.model.RoleType;
import Gdsc.web.repository.MemberRepository;
import Gdsc.web.service.MemberService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {


    private final MemberService memberService;

    @ApiOperation(value = "회원가입", notes = "회원가입 할때 쓰는 놈")
    @PostMapping("/api/join")
    public ResponseDto<Integer> join(@RequestBody Member member) {
        memberService.회원가입(member);
        // 수정필요
        return new ResponseDto<Integer>(HttpStatus.OK, 1, "성공");
    }


    @ApiOperation(value = "관리자 권한 멤버 리스트 확인", notes = "온보딩 , 멤버 데이터 전체 봄")
    @GetMapping("/api/core/memberList")
    public List<Member> memberList() {
        return memberService.멤버리스트();
    }


    @ApiOperation(value = "관리자가 멤버 데이터 수정 할 때", notes = "관리자가 멤버데이터 수정할 때 주로 경고 수정이 주가 되지 않을까")
    @PostMapping("/api/core/update")

    public ResponseDto<Integer> updateCore(@RequestBody Member member) {
        memberService.관리자멤버정보수정(member);
        return new ResponseDto<Integer>(HttpStatus.OK, 1, "update 성공");
    }
    @GetMapping
    public ApiResponse getUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Member member =memberService.getMember(principal.getUsername());

        return ApiResponse.success("user", member);
    }
}
