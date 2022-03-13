package Gdsc.web.service;

import Gdsc.web.entity.Member;
import Gdsc.web.entity.MemberInfo;
import Gdsc.web.entity.MemberPortfolioUrl;
import Gdsc.web.model.RoleType;
import Gdsc.web.repository.memberPortfolioUrl.JpaMemberPortfolioUrl;
import Gdsc.web.repository.memberinfo.JpaMemberInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import Gdsc.web.repository.member.JpaMemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final JpaMemberRepository memberRepository;
    private final JpaMemberPortfolioUrl jpaMemberPortfolioUrl;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void 회원가입(Member member) {
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRole(RoleType.MEMBER);
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setMember(member);
        member.setMemberInfo(memberInfo);
        validateDuplicateUsername(member);
        memberRepository.save(member);


    }

    @Transactional(readOnly = true)
    public List<Member> 멤버리스트() {
        return memberRepository.findAll();
    }
    //중복 유저네임 확인 로직
    private void validateDuplicateUsername(Member member) {
        Member find = memberRepository.findByUserId(member.getUserId());
        if(find != null) throw new IllegalStateException("이미 존재하는 아이디 입니다.");
    }



    public Member getUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }
    @Transactional
    public void 정보업데이트(String userId , MemberInfo requestMemberInfo){
        Member member = memberRepository.findByUserId(userId);
        if(member==null) throw new IllegalArgumentException("없는 사용자 입니다. ");
        MemberInfo memberInfo = member.getMemberInfo();
        memberInfo.setGeneration(requestMemberInfo.getGeneration());
        memberInfo.setBirthday(requestMemberInfo.getBirthday());
        memberInfo.setIntroduce(requestMemberInfo.getIntroduce());
        memberInfo.setGitEmail(requestMemberInfo.getGitEmail());
        memberInfo.setMajor(requestMemberInfo.getMajor());
        memberInfo.setHashTag(requestMemberInfo.getHashTag());
        memberInfo.setNickname(requestMemberInfo.getNickname());
        memberInfo.setPhoneNumber(requestMemberInfo.getPhoneNumber());
        memberInfo.setPositionType(requestMemberInfo.getPositionType());
        jpaMemberPortfolioUrl.deleteMemberPortfolioUrlsByMemberInfo(memberInfo);
        for (MemberPortfolioUrl memberPortfolioUrl:requestMemberInfo.getMemberPortfolioUrls()) {
            memberPortfolioUrl.setMemberInfo(memberInfo);
        }
        memberInfo.setMemberPortfolioUrls(requestMemberInfo.getMemberPortfolioUrls());

    }
    public boolean 닉네임중복검사(String nickname){
        return memberRepository.existsByMemberInfo_Nickname(nickname);
    }
}
