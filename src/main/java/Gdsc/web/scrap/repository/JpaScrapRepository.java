package Gdsc.web.scrap.repository;

import Gdsc.web.scrap.mapper.MemberScrapPostResponseMapping;
import Gdsc.web.scrap.entity.MemberScrapPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaScrapRepository extends JpaRepository<MemberScrapPost, Integer> {
    MemberScrapPost findByUserIdAndPost_PostId(String userId, Long postId);
    List<MemberScrapPost> findByUserId(String userId);


}
