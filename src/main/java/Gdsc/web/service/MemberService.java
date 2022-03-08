package Gdsc.web.service;

import Gdsc.web.entity.Member;
import Gdsc.web.entity.MemberInfo;
import Gdsc.web.model.RoleType;
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
    private final JpaMemberInfoRepository jpaMemberInfoRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void 회원가입(Member member) {
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRole(RoleType.MEMBER);
        MemberInfo memberInfo = new MemberInfo();
        member.setMemberInfo(memberInfo);
        memberInfo.setUserID(member.getUserId());
        validateDuplicateUsername(member);
        jpaMemberInfoRepository.save(memberInfo);
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

    public void 정보업데이트(String userId , MemberInfo requestMemberInfo){
        MemberInfo memberInfo = jpaMemberInfoRepository.findByUserID(userId)
                .orElseThrow(()-> new IllegalArgumentException("없는 사용자 입니다. "));
        memberInfo.setGeneration(requestMemberInfo.getGeneration());
        memberInfo.setBirthday(requestMemberInfo.getBirthday());
        memberInfo.setIntroduce(requestMemberInfo.getIntroduce());
        memberInfo.setGitEmail(requestMemberInfo.getGitEmail());
        memberInfo.setMajor(requestMemberInfo.getMajor());
        memberInfo.setHashTag(requestMemberInfo.getHashTag());
        memberInfo.setNickName(requestMemberInfo.getNickName());
        memberInfo.setPhoneNumber(requestMemberInfo.getPhoneNumber());
        memberInfo.setPositionType(requestMemberInfo.getPositionType());
    }
}
