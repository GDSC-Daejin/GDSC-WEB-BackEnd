package Gdsc.web.member.repository;

import Gdsc.web.member.entity.Member;
import Gdsc.web.member.entity.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaMemberInfoRepository extends JpaRepository<MemberInfo, Integer> {
    Optional<MemberInfo> findByMember(Member member);
}
