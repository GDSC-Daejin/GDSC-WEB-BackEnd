package Gdsc.web.common.service;

import Gdsc.web.member.dto.MemberInfoResponseServerDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public  class RestTemplateService {
    private static final String AuthServerURL = "http://localhost:8100";
    public MemberInfoResponseServerDto getNicknameImage(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(
                AuthServerURL +"/{userId}",
                MemberInfoResponseServerDto.class, userId);
    }
}
