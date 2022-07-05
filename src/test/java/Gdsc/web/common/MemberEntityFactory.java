package Gdsc.web.common;

import Gdsc.web.member.entity.Member;
import Gdsc.web.member.entity.MemberInfo;
import Gdsc.web.member.model.RoleType;
import Gdsc.web.oauth.entity.ProviderType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberEntityFactory {
    public static Member guestMemberEntity() {
        LocalDateTime now = LocalDateTime.now();
        MemberInfo memberInfo = new MemberInfo();
        Member member = new Member(
                "user",
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
        member.setPassword("password");
        memberInfo.setMember(member);
        memberInfo.setPhoneNumber("010-1234-5678");


        return member;
    }
    public static Member memberEntity() {
        LocalDateTime now = LocalDateTime.now();
        MemberInfo memberInfo = new MemberInfo();
        Member member = new Member(
                "user",
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
        member.setPassword("password");
        memberInfo.setMember(member);


        return member;
    }

    public static Member adminMemberEntity() {
        LocalDateTime now = LocalDateTime.now();
        MemberInfo memberInfo = new MemberInfo();
        Member member = new Member(
                "user",
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
        member.setPassword("password");
        memberInfo.setMember(member);


        return member;
    }
}
