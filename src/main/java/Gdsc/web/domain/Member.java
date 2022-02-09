package Gdsc.web.domain;

import Gdsc.web.config.oauth.model.ProviderType;
import Gdsc.web.model.PositionType;
import Gdsc.web.model.RoleType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    @JsonProperty("id")
    @ApiModelProperty(example = "1 ---Insert 시  Auto Increament로 넣지말아요")
    private int id;

    @Column(nullable = false)
    @ApiModelProperty(example = "gudcks305")
    String username;
    @ApiModelProperty(example = "$10$8lDyClwH.ET3BA44inQLKuRNISg4paTPwgD2V5pw/RMmtTGJvhPvy")
    @Column(nullable = false)
    String password;
    @ApiModelProperty(example = "gudcksegeg@gmail.com")
    @Column
    String email;

    @Column
    @ApiModelProperty(example = "나는 위대한 사람")
    private String introduce;

    @Column
    @ApiModelProperty(example = "이미지 URL 넣을것")
    private String memberImg;

    @Column(length = 30)
    @ApiModelProperty(example = "홍길동")
    String name;



    @Column(length = 30)
    @ApiModelProperty(example = "010-9132-1234")
    String phoneNumber;
    @Enumerated(EnumType.STRING)

    @OneToOne
    @JoinColumn(name = "emailOnBoarding" )
    private OnboardingMember onboardingMember;
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(example = "MEMBER ---Insert 시기본값 MEMBER 로 회원가입 넣지말아요")
    private RoleType role; // 멤버 리드

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(example = "Backend")
    private PositionType positionType; //백엔든지 프론트인지

    @CreationTimestamp
    @ApiModelProperty(example = "2022-01-06 14:57:42.777000 ---Insert 시 자동 삽입 넣지말아요")
    private Timestamp uploadDate;

    @ColumnDefault("0")
    @ApiModelProperty(example = "0 ---Insert 회원가입시 기본 0 넣지말아요")
    private int warning;

    @Column
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    public Member update(String name, String memberImg , String email) {
        this.name = name;
        this.memberImg = memberImg;
        this.email = email;
        return this;
    }

}
