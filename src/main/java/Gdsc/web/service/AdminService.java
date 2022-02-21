package Gdsc.web.service;

import Gdsc.web.entity.Member;
import Gdsc.web.repository.member.JpaMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j // 디버그를 위한 로그 설정
@Service
@RequiredArgsConstructor
public class AdminService {

    private final JpaMemberRepository repository;

    @Transactional
    public void 맴버권한수정(final Member member){
        // Validations
        validate(member);

        final Member original = repository.findByUserId(member.getUserId());
        original.setRole(member.getRole());

    }

    @Transactional(readOnly = true)
    public List<Member> 멤버목록(){
        return repository.findMember();
    }

    @Transactional(readOnly = true)
    public List<Member> 게스트목록(){
        return repository.findGUEST();
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