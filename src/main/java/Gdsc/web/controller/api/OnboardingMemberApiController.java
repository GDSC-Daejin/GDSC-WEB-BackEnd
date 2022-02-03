package Gdsc.web.controller.api;

import Gdsc.web.domain.OnboardingMember;
import Gdsc.web.domain.OnboardingMemberMapping;
import Gdsc.web.service.OnboardingService;
import Gdsc.web.controller.dto.ResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OnboardingMemberApiController {


    private final OnboardingService onboardingService;
    private String msg;
    @ApiOperation(value = "온보딩 정보 추가", notes ="온보딩 페이지 정보 넣는 api")
    @PostMapping("/api/member/onBoarding/join")
    public ResponseDto<Boolean> save(@RequestBody OnboardingMember onboardingMember /*, @AuthenticationPrincipal PrincipalDetails principalDetails*/) {
        boolean result = onboardingService.온보딩정보추가(onboardingMember /*, principalDetails.getUsername()*/);
        if (result == false) msg = "성공";
        else msg = "이메일 또는 닉네임 중복 입니다";
        return new ResponseDto<Boolean>(HttpStatus.OK, result , msg);
    }

    @ApiOperation(value = "닉네임 리스트", notes ="닉네임을 리스트 형식으로 반환 받기")
    @GetMapping("/api/member/onBoarding/nickname")
    public ResponseDto<List<OnboardingMemberMapping>> getNickNameList() {
        return new ResponseDto<>(HttpStatus.OK, onboardingService.닉네임리스트() , "닉네임 리스트");
    }

}
