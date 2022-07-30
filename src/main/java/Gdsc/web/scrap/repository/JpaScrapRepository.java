package Gdsc.web.scrap.repository;

import Gdsc.web.scrap.mapper.MemberScrapPostResponseMapping;
import Gdsc.web.member.entity.MemberInfo;
import Gdsc.web.scrap.entity.MemberScrapPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaScrapRepository extends JpaRepository<MemberScrapPost, Integer> {
    MemberScrapPost findByMemberInfo_Member_UserIdAndPost_PostId(String userId, Long postId);
    Page<MemberScrapPostResponseMapping> findByMemberInfo(MemberInfo memberInfo, Pageable pageable);


}
