package Gdsc.web.service;

import Gdsc.web.dto.WarningDto;
import Gdsc.web.entity.Member;
import Gdsc.web.entity.WarnDescription;
import Gdsc.web.model.RoleType;
import Gdsc.web.repository.member.JpaMemberRepository;
import Gdsc.web.repository.warnDescription.JpaWarnDescription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j // 디버그를 위한 로그 설정
@Service
@RequiredArgsConstructor
public class AdminService {

    private final JpaMemberRepository repository;
    private final JpaWarnDescription jpaWarnDescription;
    @Transactional
    public void 맴버권한수정(final Member member){
        // Validations
        validate(member);

        final Member original = repository.findByUserId(member.getUserId());
        original.setRole(member.getRole());

    }

    @Transactional(readOnly = true)
    public List<Member> 멤버목록(){
        List<RoleType> roleTypes = new ArrayList<>();
        roleTypes.add(RoleType.CORE);
        roleTypes.add(RoleType.LEAD);
        roleTypes.add(RoleType.MEMBER);
        return repository.findMembersByRoleInAndMemberInfo_PhoneNumberIsNotNull(roleTypes);
    }

    @Transactional(readOnly = true)
    public List<Member> 게스트목록(){
        List<RoleType> roleTypes = new ArrayList<>();
        roleTypes.add(RoleType.GUEST);
        return repository.findMembersByRoleInAndMemberInfo_PhoneNumberIsNotNull(roleTypes);
    }

    @Transactional
    public void 경고주기(String fromUser, WarningDto warningDto) {
        Member admin = repository.findByUserId(fromUser);
        Member ToUser = repository.findByUserId(warningDto.getToUser());
        WarnDescription warnDescription = new WarnDescription();
        warnDescription.setFromUser(admin);
        warnDescription.setContent(warningDto.getContent());
        warnDescription.setTitle(warningDto.getTitle());
        warnDescription.setToUser(ToUser);
        jpaWarnDescription.save(warnDescription);
    }
    // 유효성 검사
    private void validate(final Member member){
        if(member == null){
            log.warn("Domain cannot be null.");
            throw new RuntimeException("Domain cannot be null");
        }

        if(member.getUsername() == null){
            log.warn("없는 사용자 입니다.");
            throw new RuntimeException("없는 사용자 입니다.");
        }
    }
}