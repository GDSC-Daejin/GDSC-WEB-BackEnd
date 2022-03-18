package Gdsc.web.repository.scrap;

import Gdsc.web.entity.MemberScrapPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaScrapRepository extends JpaRepository<MemberScrapPost, Integer> {
    MemberScrapPost findByMemberInfo_Member_UserIdAndPost_PostId(String userId, Long postId);
}
