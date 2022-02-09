package Gdsc.web.service;

import Gdsc.web.domain.Member;
import Gdsc.web.domain.OnboardingMember;
import Gdsc.web.model.RoleType;
import Gdsc.web.repository.OnboardingMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import Gdsc.web.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final OnboardingMemberRepository onboardingMemberRepository;
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

    @Transactional
    public void 관리자멤버정보수정(Member requestMember){
        Member member = memberRepository.findByUsername(requestMember.getUsername())
                .orElseThrow(()->{
                    return new IllegalArgumentException("없는 사용자");
                });
        member.setWarning(requestMember.getWarning());
        member.setProfileImageUrl(requestMember.getProfileImageUrl());
        member.setIntroduce(requestMember.getIntroduce());
        member.setPhoneNumber(requestMember.getPhoneNumber());
        member.setName(requestMember.getName());
        member.setPositionType(requestMember.getPositionType());
        member.setRole(requestMember.getRole());

        OnboardingMember onboardingMember = onboardingMemberRepository.findByEmail(requestMember.getUsername())
                .orElseThrow(()->{
                    return new IllegalStateException("없는 사용자 입니다");
                });
        onboardingMember.setInterest(member.getOnboardingMember().getInterest());
        onboardingMember.setMajor(member.getOnboardingMember().getMajor());
        onboardingMember.setNickname(member.getOnboardingMember().getNickname());
    }

    public Member getMember(String userId) {
        return memberRepository.findByUserId(userId);
    }
}
