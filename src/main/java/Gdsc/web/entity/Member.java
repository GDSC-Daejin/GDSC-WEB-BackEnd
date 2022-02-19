package Gdsc.web.entity;


import Gdsc.web.model.PositionType;
import Gdsc.web.model.RoleType;
import Gdsc.web.oauth.entity.ProviderType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member {


    @Id
    @Column(name = "USER_ID", length = 64, unique = true)
    @NotNull
    @Size(max = 64)
    private String userId;

    @Column(nullable = false)
    @ApiModelProperty(example = "유형찬")
    String username;
    @ApiModelProperty(example = "$10$8lDyClwH.ET3BA44inQLKuRNISg4paTPwgD2V5pw/RMmtTGJvhPvy")
    @Column(nullable = false)
    String password;
    @ApiModelProperty(example = "gudcksegeg@gmail.com")
    @Column(nullable = false)
    String email;

    @Column(name = "EMAIL_VERIFIED_YN", length = 1)
    @NotNull
    @Size(min = 1, max = 1)
    private String emailVerifiedYn;




    @Column(name = "PROFILE_IMAGE_URL", length = 512)
    @NotNull
    @Size(max = 512)
    private String profileImageUrl;



    @Enumerated(EnumType.STRING)
    @ApiModelProperty(example = "MEMBER ---Insert 시기본값 GUEST 로 회원가입 넣지말아요")
    private RoleType role; // 멤버 리드



    @Column
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @JoinColumn(name = "MEMBER_INFO_ID")
    @OneToOne
    private MemberInfo memberInfo;

    @Column(name = "MODIFIED_AT")
    @NotNull
    private LocalDateTime modifiedAt;

    @CreationTimestamp
    @ApiModelProperty(example = "2022-01-06 14:57:42.777000 ---Insert 시 자동 삽입 넣지말아요")
    private LocalDateTime uploadDate;


    public Member(
            @NotNull @Size(max = 64) String userId,
            @NotNull @Size(max = 100) String username,
            @NotNull @Size(max = 512) String email,
            @NotNull @Size(max = 1) String emailVerifiedYn,
            @NotNull @Size(max = 512) String profileImageUrl,
            @NotNull ProviderType providerType,
            @NotNull RoleType role,
            @NotNull LocalDateTime modifiedAt,
            @NotNull LocalDateTime uploadDate,
            @NotNull MemberInfo memberInfo

    ) {
        this.userId = userId;
        this.username = username;
        this.password = "NO_PASS";
        this.email = email != null ? email : "NO_EMAIL";
        this.emailVerifiedYn = emailVerifiedYn;
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : "";
        this.providerType = providerType;
        this.role = role;
        this.uploadDate = uploadDate;
        this.modifiedAt = modifiedAt;
        this.memberInfo = memberInfo;

    }


}
