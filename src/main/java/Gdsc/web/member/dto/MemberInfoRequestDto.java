package Gdsc.web.member.dto;

import Gdsc.web.member.model.PositionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MemberInfoRequestDto {


    private Integer generation;
    private String introduce;

    private String nickname;

    private String phoneNumber;

    private String major;

    private String gitEmail;

    private String studentID;

    private PositionType positionType;
    private String hashTag;
    private String gitHubUrl;
    private String blogUrl;
    private String etcUrl;


    private LocalDateTime birthday;


}
