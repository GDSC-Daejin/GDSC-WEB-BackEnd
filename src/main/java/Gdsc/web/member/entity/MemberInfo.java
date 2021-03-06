package Gdsc.web.member.entity;

import Gdsc.web.member.dto.MemberInfoResponseDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@Entity
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class MemberInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_INFO_ID")
    private int memberInfoId;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "USER_ID")
    @JsonIgnore
    private Member member;

    @Column(name = "GDSC_GENERATION")
    private Integer generation;
    @Column
    @ApiModelProperty(example = "나는 위대한 사람")
    private String introduce;

    @Column(length = 30 ,unique = true)
    @ApiModelProperty(example = "Rocoli")
    private String nickname;

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

    @ApiModelProperty(example = "Backend")
    @Column(name = "POSITION_TYPE")
    private Gdsc.web.member.model.PositionType PositionType;

    @Column(name = "HashTag")
    private String hashTag;

    /*/// not table 속성
    @OneToMany(mappedBy = "memberInfo")
    private List<MemberScrapPost> memberScrapPostList;

    @OneToMany(mappedBy = "memberInfo" , cascade = CascadeType.REMOVE)
    private List<Post> myPost;
*/  @Column(name = "GIT_URL")
    private String gitHubUrl;
    @Column(name = "BLOG_URL")
    private String blogUrl;
    @Column(name = "ETC_URL")
    private String etcUrl;


    @ApiModelProperty(example = "1998-07-09 00:00:00.000000")
    private LocalDateTime birthday;

    @Column(name = "MODIFIED_AT")
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @CreationTimestamp
    @ApiModelProperty(example = "2022-01-06 14:57:42.777000 ---Insert 시 자동 삽입 넣지말아요")
    private LocalDateTime uploadDate;


    public MemberInfoResponseDto toMemberInfoResponseDto() {
        return new MemberInfoResponseDto(nickname , member.toResponseDto());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MemberInfo that = (MemberInfo) o;
        return  Objects.equals(memberInfoId, that.memberInfoId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
