package Gdsc.web.service;

import Gdsc.web.domain.Member;
import Gdsc.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j // 디버그를 위한 로그 설정
@Service
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository repository;

    @Transactional
    public void 맴버권한수정(final Member member){
        // Validations
        validate(member);

        final Optional<Member> original = repository.findByUsername(member.getUsername());

        original.ifPresent(mem ->{
            mem.setRole(member.getRole());
            repository.save(mem);
        });
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
