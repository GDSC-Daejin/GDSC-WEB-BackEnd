package Gdsc.web.repository.scrap;

import Gdsc.web.dto.mapping.MemberScrapPostResponseMapping;
import Gdsc.web.entity.MemberInfo;
import Gdsc.web.entity.MemberScrapPost;
import Gdsc.web.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaScrapRepository extends JpaRepository<MemberScrapPost, Integer> {
    MemberScrapPost findByMemberInfo_Member_UserIdAndPost_PostId(String userId, Long postId);
    Page<MemberScrapPostResponseMapping> findByMemberInfo(MemberInfo memberInfo, Pageable pageable);


}
