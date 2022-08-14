package Gdsc.web.member.service;

import Gdsc.web.member.dto.MemberInfoResponseServerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {


    private static final String AuthServerURL = "http://localhost:8100";
    public MemberInfoResponseServerDto getNicknameImage(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        MemberInfoResponseServerDto memberInfo = restTemplate.getForObject(
                AuthServerURL +"/internal/member/api/memberInfo/{userId}",
                MemberInfoResponseServerDto.class, userId);
        return memberInfo;
    }
    public List<MemberInfoResponseServerDto> getNicknameImages() {
        RestTemplate restTemplate = new RestTemplate();
        MemberInfoResponseServerDto[] memberInfos = restTemplate.getForObject(
                AuthServerURL +"/internal/member/api/memberInfo",
                MemberInfoResponseServerDto[].class);
        assert memberInfos != null;
        return Arrays.asList(memberInfos);
    }



}
