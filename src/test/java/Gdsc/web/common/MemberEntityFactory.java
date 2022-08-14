package Gdsc.web.common;

import Gdsc.web.member.entity.Member;
import Gdsc.web.member.entity.MemberInfo;
import Gdsc.web.member.model.RoleType;
import Gdsc.web.oauth.entity.ProviderType;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Data
public class MemberEntityFactory {
    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public static Member guestMemberEntity() {
        LocalDateTime now = LocalDateTime.now();
        MemberInfo memberInfo = new MemberInfo();
        Member member = new Member(
                "guest",
                "guestName",
                "guestEmail@gmail.com",
                "Y",
                "imageUrl",
                ProviderType.GOOGLE,
                RoleType.GUEST,
                now,
                now,
                memberInfo
        );
        member.setPassword(passwordEncoder.encode("password"));
        memberInfo.setMember(member);
        memberInfo.setPhoneNumber("010-1234-5678");


        return member;
    }
    public static Member memberEntity() {
        LocalDateTime now = LocalDateTime.now();
        MemberInfo memberInfo = new MemberInfo();
        Member member = new Member(
                "member",
                "memberName",
                "memberEmail@gmail.com"
                , "Y",
                "imageUrl",
                ProviderType.GOOGLE,
                RoleType.MEMBER,
                now,
                now,
                memberInfo
        );
        member.setPassword(passwordEncoder.encode("password"));
        memberInfo.setMember(member);


        return member;
    }

    public static Member adminMemberEntity() {
        LocalDateTime now = LocalDateTime.now();
        MemberInfo memberInfo = new MemberInfo();
        Member member = new Member(
                "admin",
                "adminName",
                "AdminEmail@gmail.com"
                , "Y",
                "imageUrl",
                ProviderType.GOOGLE,
                RoleType.LEAD,
                now,
                now,
                memberInfo
        );
        member.setPassword(passwordEncoder.encode("password"));
        memberInfo.setMember(member);


        return member;
    }
}
