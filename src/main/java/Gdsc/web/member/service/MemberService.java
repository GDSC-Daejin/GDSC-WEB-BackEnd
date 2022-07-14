package Gdsc.web.member.service;

import Gdsc.web.member.dto.MemberInfoRequestDto;
import Gdsc.web.member.entity.Member;
import Gdsc.web.member.entity.MemberInfo;
import Gdsc.web.member.model.RoleType;
import Gdsc.web.member.repository.JpaMemberInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import Gdsc.web.member.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JpaMemberInfoRepository jpaMemberInfoRepository;
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
    public void 정보업데이트(String userId , MemberInfoRequestDto requestMemberInfo){
        Member member = memberRepository.findByUserId(userId);
        if(member==null) throw new IllegalArgumentException("없는 사용자 입니다. ");
        MemberInfo memberInfo = member.getMemberInfo();

        memberInfo.setIntroduce(requestMemberInfo.getIntroduce());
        memberInfo.setBirthday(requestMemberInfo.getBirthday());

        memberInfo.setGeneration(requestMemberInfo.getGeneration());
        memberInfo.setHashTag(requestMemberInfo.getHashTag());
        // 정규식 체크 데이터 유효성 검사
        memberInfo.setGitEmail(regularExpressionEmail(requestMemberInfo.getGitEmail()) ?
                requestMemberInfo.getGitEmail() : memberInfo.getGitEmail());
        memberInfo.setPhoneNumber(regularExpressionPhoneNumber(requestMemberInfo.getPhoneNumber()) ?
                requestMemberInfo.getPhoneNumber() : memberInfo.getPhoneNumber());
        memberInfo.setNickname(regularExpressionNickname(requestMemberInfo.getNickname()) ?
                requestMemberInfo.getNickname() : memberInfo.getNickname());
        memberInfo.setMajor(requestMemberInfo.getMajor());
        memberInfo.setStudentID(requestMemberInfo.getStudentID());
        memberInfo.setPositionType(requestMemberInfo.getPositionType());
        memberInfo.setBlogUrl(requestMemberInfo.getBlogUrl());
        memberInfo.setEtcUrl(requestMemberInfo.getEtcUrl());
        memberInfo.setGitHubUrl(requestMemberInfo.getGitHubUrl());
        jpaMemberInfoRepository.save(memberInfo);


    }
    // url은 고려를 ...
    public boolean regularExpressionUrl(String url){
        return url.matches("^(https?://)?(www\\.)?([-a-z0-9]+\\.)*[-a-z0-9]+\\.[a-z]{2,}(/[-a-z0-9_\\s/?=]+)*$");
    }
    public boolean regularExpressionNickname(String nickname){
        String regex = "^[a-zA-Z]{2,10}$";
        return nickname.matches(regex);
    }
    private boolean regularExpressionEmail(String email){
        String regex = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,10}.[a-zA-Z0-9]{2,10}$";
        return email.matches(regex);
    }
    public boolean regularExpressionPhoneNumber(String phoneNumber){
        return phoneNumber.matches("^01(?:0|1|[6-9])[-]?(\\d{3}|\\d{4})[-]?(\\d{4})$");
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
