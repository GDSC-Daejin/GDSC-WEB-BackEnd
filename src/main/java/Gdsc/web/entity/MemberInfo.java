package Gdsc.web.entity;

import Gdsc.web.model.PositionType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_INFO_ID")
    private int memberInfoId;

    @Column(name = "USER_ID")
    private String userID;

    @Column
    @ApiModelProperty(example = "나는 위대한 사람")
    private String introduce;

    @Column(length = 30)
    @ApiModelProperty(example = "Rocoli")
    private String nickName;

    @Column(length = 30)
    @ApiModelProperty(example = "010-9132-1234")
    private String phoneNumber;

    @Column
    @ApiModelProperty(example = "산업경영공학과")
    private String major;

    @Column(name = "GIT_EMAIL" , length = 30)
    @ApiModelProperty(example = "gudcks0305")
    private String gitEmail;

    @Column(length = 30)
    @ApiModelProperty(example = "20177878")
    private String StudentID;

    @Column(name = "POSITION_TYPE")
    private Gdsc.web.model.PositionType PositionType;

    @Column(name = "HashTag")
    private String hashTag;

    /// not table 속성
    @OneToMany(mappedBy = "memberInfo")
    private List<MemberScrapPost> memberScrapPostList;

    @OneToMany(mappedBy = "memberInfo")
    private List<Post> mypost;


    @Column(name = "MODIFIED_AT")
    @LastModifiedDate
    @NotNull
    private LocalDateTime modifiedAt;

    @CreationTimestamp
    @ApiModelProperty(example = "2022-01-06 14:57:42.777000 ---Insert 시 자동 삽입 넣지말아요")
    private LocalDateTime uploadDate;
}
