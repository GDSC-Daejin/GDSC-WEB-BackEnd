package Gdsc.web.dto.requestDto;

import Gdsc.web.model.RoleType;
import lombok.Data;

@Data
public class MemberRoleUpdateDto {
    private String userId;
    private RoleType role;
}
