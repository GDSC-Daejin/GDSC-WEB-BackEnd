package Gdsc.web.scrap.repository;

import Gdsc.web.scrap.mapper.MemberScrapPostResponseMapping;
import Gdsc.web.scrap.entity.MemberScrapPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaScrapRepository extends JpaRepository<MemberScrapPost, Integer> {
    MemberScrapPost findByUserIdAndPost_PostId(String userId, Long postId);
    Page<MemberScrapPostResponseMapping> findByUserId(String userId, Pageable pageable);


}
