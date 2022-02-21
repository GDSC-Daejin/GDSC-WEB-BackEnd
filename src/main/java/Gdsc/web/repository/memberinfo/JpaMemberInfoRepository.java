package Gdsc.web.repository.memberinfo;

import Gdsc.web.entity.Member;
import Gdsc.web.entity.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberInfoRepository extends JpaRepository<MemberInfo, Integer> {
}
