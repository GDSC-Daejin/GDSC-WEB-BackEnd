package Gdsc.web.admin.dto;

import Gdsc.web.member.model.RoleType;
import lombok.Data;

@Data
public class MemberRoleUpdateDto {
    private String userId;
    private RoleType role;
}
