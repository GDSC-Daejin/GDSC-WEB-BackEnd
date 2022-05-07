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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final JpaMemberRepository memberRepository;
    private final JpaMemberInfoRepository jpaMemberInfoRepository;
    private final JpaMemberPortfolioUrl jpaMemberPortfolioUrl;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void 회원가입(Member member) {
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRole(RoleType.MEMBER);
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setMember(member);
        member.setMemberInfo(memberInfo);
        List<MemberPortfolioUrl> memberPortfolioUrls = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            memberPortfolioUrls.add(new MemberPortfolioUrl(memberInfo));
        }
        memberInfo.setMemberPortfolioUrls(memberPortfolioUrls);
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
        // if requestMemberInfo's element is null, it means that user didn't change element.
        // stream.filter(element -> element != null)
        // Call all setter functions of MemberInfo class. And set the value to memberInfo. And requestMemberInfo is the value that user inputted. requestMemberInfo's element is null, it means that user didn't change element.




        if(requestMemberInfo.getIntroduce() != null) member.getMemberInfo().setIntroduce(requestMemberInfo.getIntroduce());
        if(requestMemberInfo.getBirthday() != null)  member.getMemberInfo().setBirthday(requestMemberInfo.getBirthday());
        if(requestMemberInfo.getGitEmail() != null) member.getMemberInfo().setGitEmail(requestMemberInfo.getGitEmail());
        if(requestMemberInfo.getGeneration() != null) member.getMemberInfo().setGeneration(requestMemberInfo.getGeneration());
        if(requestMemberInfo.getHashTag() != null) member.getMemberInfo().setHashTag(requestMemberInfo.getHashTag());
        if(requestMemberInfo.getPhoneNumber() != null) member.getMemberInfo().setPhoneNumber(requestMemberInfo.getPhoneNumber());
        if(requestMemberInfo.getMajor() != null) member.getMemberInfo().setMajor(requestMemberInfo.getMajor());
        if(requestMemberInfo.getStudentID() != null) member.getMemberInfo().setStudentID(requestMemberInfo.getStudentID());
        if(requestMemberInfo.getPositionType() != null) member.getMemberInfo().setPositionType(requestMemberInfo.getPositionType());
        if(requestMemberInfo.getNickname() != null && !닉네임중복검사(requestMemberInfo.getNickname())) member.getMemberInfo().setNickname(requestMemberInfo.getNickname());

        if(requestMemberInfo.getMemberPortfolioUrls() != null) {
            List<MemberPortfolioUrl> memberPortfolioUrls = member.getMemberInfo().getMemberPortfolioUrls();
            if(requestMemberInfo.getMemberPortfolioUrls().size() > memberPortfolioUrls.size()) {
                for(int i = memberPortfolioUrls.size(); i < requestMemberInfo.getMemberPortfolioUrls().size(); i++) {
                    memberPortfolioUrls.add(new MemberPortfolioUrl(member.getMemberInfo()));
                }
            } else if (requestMemberInfo.getMemberPortfolioUrls().size() < memberPortfolioUrls.size()) {
                for(int i = memberPortfolioUrls.size() - 1; i >= requestMemberInfo.getMemberPortfolioUrls().size(); i--) {
                    memberPortfolioUrls.remove(i);
                }
            } else {
                for (int i = 0; i < memberPortfolioUrls.size(); i++) {
                    if(requestMemberInfo.getMemberPortfolioUrls().get(i).getWebUrl() != null) {
                        memberPortfolioUrls.get(i).setWebUrl(requestMemberInfo.getMemberPortfolioUrls().get(i).getWebUrl());
                    }
                }
            }

        }



    }
   /* public <T> boolean elementNullCheck(T requestMemberInfo) {
        if(requestMemberInfo == null) return true;
        else return false;
    }
    public <T> void updateElement(T requestMemberInfo , Member member) {
        Arrays.stream()
        if (requestMemberInfo instanceof MemberInfo) {
            MemberInfo memberInfo = (MemberInfo) requestMemberInfo;
        }
    }*/
    @Transactional
    public boolean 닉네임중복검사(String nickname){
        return memberRepository.existsByMemberInfo_Nickname(nickname);
    }

    @Transactional
    public void deleteMemberForTest(String userId){
        Member member = memberRepository.findByUserId(userId);
        memberRepository.delete(member);
    }
}
