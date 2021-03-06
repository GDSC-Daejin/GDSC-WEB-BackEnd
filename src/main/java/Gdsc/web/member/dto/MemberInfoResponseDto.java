package Gdsc.web.member.dto;

import lombok.Data;

@Data
public class MemberInfoResponseDto {
    private String nickname;
    private MemberResponseDto member;
    public MemberInfoResponseDto(String nickname , MemberResponseDto member) {
        this.nickname = nickname;
        this.member = member;
    }

}
