package Gdsc.web.service;

import Gdsc.web.entity.Member;
import Gdsc.web.entity.MemberInfo;
import Gdsc.web.model.RoleType;
import Gdsc.web.repository.MemberInfoRepository;
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
    private final MemberInfoRepository memberInfoRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void 회원가입(Member member) {
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRole(RoleType.MEMBER);
        MemberInfo memberInfo = new MemberInfo();
        member.setMemberInfo(memberInfo);
        memberInfo.setUserID(member.getUserId());
        validateDuplicateUsername(member);
        memberInfoRepository.save(memberInfo);
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
