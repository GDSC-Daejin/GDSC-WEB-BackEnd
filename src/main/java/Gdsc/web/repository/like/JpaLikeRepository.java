package Gdsc.web.repository.like;

import Gdsc.web.entity.Likes;
import Gdsc.web.entity.MemberInfo;
import Gdsc.web.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaLikeRepository extends JpaRepository<Likes , Integer> {
    Likes findByMemberInfo_MemberInfoIdAndPost_PostId(String userId , Long PostId);
}
