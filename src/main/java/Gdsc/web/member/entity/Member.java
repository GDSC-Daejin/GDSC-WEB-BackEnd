package Gdsc.web.member.entity;


import Gdsc.web.member.dto.MemberResponseDto;
import Gdsc.web.member.model.RoleType;
import Gdsc.web.oauth.entity.ProviderType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    private ProviderType providerType; // 어떤 소셜로그인인지 파악

    @JoinColumn(name = "MEMBER_INFO_ID")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private MemberInfo memberInfo;

    @Column(name = "MODIFIED_AT")
    @LastModifiedDate
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
    public MemberResponseDto toResponseDto(){
        return new MemberResponseDto(this.profileImageUrl);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Member member = (Member) o;
        return userId != null && Objects.equals(userId, member.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}