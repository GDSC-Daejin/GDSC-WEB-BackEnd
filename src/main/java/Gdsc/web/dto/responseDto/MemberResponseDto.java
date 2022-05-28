package Gdsc.web.dto.responseDto;

import lombok.Data;

@Data
public class MemberResponseDto {
    private String profileImageUrl;

    public MemberResponseDto(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
