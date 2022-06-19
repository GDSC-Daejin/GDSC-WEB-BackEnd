package Gdsc.web.config;

import Gdsc.web.annotation.WithMockUser;
import Gdsc.web.entity.Member;
import Gdsc.web.entity.MemberInfo;
import Gdsc.web.oauth.entity.UserPrincipal;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.LocalDateTime;

public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        LocalDateTime now = LocalDateTime.now();
        Member member = Member.builder()
                .userId("test")
                .email("test@mail.com")
                .password("password")
                .username("test")
                .profileImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRj5S7Pg5Gi3ytwuz5n38i_U71U_E1oFuN5eg&usqp=CAU")
                .build();
        MemberInfo memberInfo = MemberInfo.builder()
                .birthday(now)
                .blogUrl("http://blog.com")
                .gitHubUrl("http://github.com")
                .etcUrl("http://etc.com")
                .generation(1)
                .hashTag("gdsc,gdsc-web")
                .major("컴퓨터공학")
                .nickname("임시이름")
                .phoneNumber("010-1234-5678")
                .StudentID("123456789")
                .introduce("임시소개")
                .member(member)
                .build();
        member.setMemberInfo(memberInfo);
        UserPrincipal principal = UserPrincipal.create(member);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
        context.setAuthentication(authentication);
        return context;
    }
}
