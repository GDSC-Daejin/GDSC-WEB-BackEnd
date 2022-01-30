package Gdsc.web.service;

import Gdsc.web.domain.Member;
import Gdsc.web.domain.OnboardingMember;
import Gdsc.web.domain.OnboardingMemberMapping;
import Gdsc.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Gdsc.web.repository.OnboardingMemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OnboardingService {

    private final OnboardingMemberRepository onboardingMemberRepository;


    private final MemberRepository memberRepository;



    @Transactional(readOnly = true)
    public List<OnboardingMemberMapping> 닉네임리스트(){
        return onboardingMemberRepository.findAllBy();
    }
/*
    @Transactional(readOnly = true)
    public List<OnboadingMemberList> 멤버리스트(){
        return onboardingMemberRepositoryAdd.findAllBy();
    }*/
    @Transactional
    public boolean 온보딩정보추가(OnboardingMember ResponseOnboardingMember /*, String username*/) {
        boolean duplicatedEmail = validateDuplicateEmail(ResponseOnboardingMember);
        boolean duplicatedNickname = validateDuplicateNickName(ResponseOnboardingMember);
        /*Member member = memberRepository.findByUsername(username)
                .orElseThrow(()->{
                    return new IllegalArgumentException("없는 사용자");
                });*/
        if(duplicatedEmail == true) return true;
        if(duplicatedNickname == true) return true;
        // 나중에는 getemail 이아니라 로그인 구현햇음 로그인한 아이디로 넣을 수 있게
        /*Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("없는 사용자");
                });
        member.setOnboardingMember(ResponseOnboardingMember);*/
        onboardingMemberRepository.save(ResponseOnboardingMember);

        return false;

    }

    //중복 이메일확인 로직
    private boolean validateDuplicateEmail(@NotNull OnboardingMember onboardingMember) {
        return onboardingMemberRepository.existsByEmail(onboardingMember.getEmail());
    }
    //중복 닉 확인 로직
    private boolean validateDuplicateNickName(@NotNull OnboardingMember onboardingMember) {
        return onboardingMemberRepository.existsByNickname(onboardingMember.getNickname());
    }


}
