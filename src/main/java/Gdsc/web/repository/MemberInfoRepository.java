package Gdsc.web.repository;

import Gdsc.web.entity.Member;
import Gdsc.web.entity.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, Integer> {
}
