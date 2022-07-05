package Gdsc.web.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberInfoRequestDto {


    private Integer generation;
    @ApiModelProperty(example = "나는 위대한 사람")
    private String introduce;

    @ApiModelProperty(example = "Rocoli")
    private String nickname;

    @ApiModelProperty(example = "010-9132-1234")
    private String phoneNumber;

    @ApiModelProperty(example = "산업경영공학과")
    private String major;

    @ApiModelProperty(example = "gudcks0305")
    private String gitEmail;

    @ApiModelProperty(example = "20177878")
    private String StudentID;

    @ApiModelProperty(example = "Backend")
    private Gdsc.web.member.model.PositionType PositionType;
    private String hashTag;
    private String gitHubUrl;
    private String blogUrl;
    private String etcUrl;


    @ApiModelProperty(example = "1998-07-09 00:00:00.000000")
    private LocalDateTime birthday;

}
