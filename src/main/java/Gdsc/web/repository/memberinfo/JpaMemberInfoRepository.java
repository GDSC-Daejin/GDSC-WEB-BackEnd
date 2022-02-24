package Gdsc.web.repository.memberinfo;

import Gdsc.web.entity.Member;
import Gdsc.web.entity.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaMemberInfoRepository extends JpaRepository<MemberInfo, Integer> {
    Optional<MemberInfo> findByUserID(String userId);
}
