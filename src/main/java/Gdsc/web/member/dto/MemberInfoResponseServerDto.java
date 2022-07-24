package com.dju.gdsc.domain.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberInfoResponseServerDto {
    private String nickname;
    private String profileImageUrl;
}
