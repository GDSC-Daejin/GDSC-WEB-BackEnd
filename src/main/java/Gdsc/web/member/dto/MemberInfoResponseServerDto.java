package Gdsc.web.member.dto;

import com.nimbusds.jose.shaded.json.annotate.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoResponseServerDto {
    private String userId;
    private String nickname;
    private String role;
    private String profileImageUrl;
}
