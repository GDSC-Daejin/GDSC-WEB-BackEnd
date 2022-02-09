package Gdsc.web.repository;

import Gdsc.web.domain.OnboardingMember;
import Gdsc.web.domain.OnboardingMemberMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OnboardingMemberRepository extends JpaRepository<OnboardingMember,Integer> {

    Optional<OnboardingMember> findByNickname(String NickName);
    Optional<OnboardingMember> findByEmail(String email);
    List<OnboardingMemberMapping> findAllBy();
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);


}
