package Gdsc.web.service;

import Gdsc.web.domain.Member;
import Gdsc.web.domain.MemberNicknameMapping;
import Gdsc.web.model.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import Gdsc.web.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void 회원가입(Member member) {
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRole(RoleType.MEMBER);
        validateDuplicateUsername(member);
        memberRepository.save(member);


    }

    @Transactional(readOnly = true)
    public List<Member> 멤버리스트() {
        return memberRepository.findAll();
    }
    //중복 유저네임 확인 로직
    private void validateDuplicateUsername(Member member) {
        memberRepository.findByUsername(member.getUsername())
                .ifPresent(m-> {
                    throw new IllegalStateException("이미 존재하는 닉네임 입니다");
                });
    }



    public Member getUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }
}
