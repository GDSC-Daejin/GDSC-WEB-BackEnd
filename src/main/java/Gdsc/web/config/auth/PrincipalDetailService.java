package Gdsc.web.config.auth;

import Gdsc.web.domain.Member;
import Gdsc.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final  MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member memberEntity = memberRepository.findByUsername(username)
                .orElseThrow(()->{
                    log.error("User not found in the database {}", username);
                    return  new IllegalArgumentException("없는 사용자 입니다.");
                });
        //System.out.println("memberEntitiy : " + memberEntity);


        return new Gdsc.web.config.auth.PrincipalDetails(memberEntity);
    }
}
