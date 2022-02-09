package Gdsc.web.repository;

import Gdsc.web.domain.Member;
import Gdsc.web.domain.OnboardingMember;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    //Optional<Member> findByEmail(String email);
    Optional<Member> findByUsername(String username);

    Member findByUserId(String id);
    Member findByEmail(String email);
}
