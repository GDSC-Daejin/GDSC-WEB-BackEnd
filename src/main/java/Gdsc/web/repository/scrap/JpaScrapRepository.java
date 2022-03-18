package Gdsc.web.repository.scrap;

import Gdsc.web.entity.MemberInfo;
import Gdsc.web.entity.MemberScrapPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaScrapRepository extends JpaRepository<MemberScrapPost, Integer> {
    MemberScrapPost findByMemberInfo_Member_UserIdAndPost_PostId(String userId, Long postId);
    Page<MemberScrapPost> findByMemberInfo(MemberInfo memberInfo, Pageable pageable);
}
