package Gdsc.web.dto.responseDto;

import Gdsc.web.entity.MemberInfo;
import lombok.Data;

@Data
public class MemberInfoResponseDto {
    private String nickname;

    public MemberInfoResponseDto(String nickname) {
        this.nickname = nickname;
    }

}
