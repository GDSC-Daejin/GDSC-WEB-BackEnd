package Gdsc.web.admin.repository;

import Gdsc.web.admin.entity.PostBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPostBlockRepository extends JpaRepository<PostBlock, Integer> {
    void deleteByPost_PostId(Long postId);
    Boolean existsByPost_PostId(Long postId);
}
