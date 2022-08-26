package Gdsc.web.member.service;

import Gdsc.web.member.dto.MemberInfoResponseServerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {


    private static final String AuthServerURL = "http://localhost:8100";
    // error handling 필요 (에러처리)
    public MemberInfoResponseServerDto getNicknameImage(String userId) {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        try {
            MemberInfoResponseServerDto memberInfo = restTemplate.getForObject(
                    AuthServerURL +"/internal/member/api/memberInfo/{userId}",
                    MemberInfoResponseServerDto.class, userId);
            return memberInfo;
        }catch (Exception e){
            log.error("에러발생 : {}", e.getMessage());
            return new MemberInfoResponseServerDto();
        }

    }
    public List<MemberInfoResponseServerDto> getNicknameImages() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            MemberInfoResponseServerDto[] memberInfo = restTemplate.getForObject(
                    AuthServerURL +"/internal/member/api/memberInfo",
                    MemberInfoResponseServerDto[].class);
            return Arrays.stream(memberInfo).collect(Collectors.toList());
        }catch (Exception e){
            log.error("에러발생 : {}", e.getMessage());
            return new ArrayList<>();
        }

    }



}
