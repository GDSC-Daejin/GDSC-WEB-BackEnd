package Gdsc.web.repository.memberinfo;

import Gdsc.web.entity.Member;
import Gdsc.web.entity.MemberInfo;
import Gdsc.web.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JpaMemberInfoRepository extends JpaRepository<MemberInfo, Integer> {
    Optional<MemberInfo> findByMember(Member member);
}
