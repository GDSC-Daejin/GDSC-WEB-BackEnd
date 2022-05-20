package Gdsc.web.common;

import Gdsc.web.entity.Member;
import Gdsc.web.entity.MemberInfo;
import Gdsc.web.model.RoleType;
import Gdsc.web.oauth.entity.ProviderType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class MemberEntityFactory {
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
        member.setPassword("password");
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
        member.setPassword("password");
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
        member.setPassword("password");
        memberInfo.setMember(member);


        return member;
    }
}
