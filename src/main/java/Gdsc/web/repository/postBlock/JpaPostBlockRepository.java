package Gdsc.web.repository.postBlock;

import Gdsc.web.entity.PostBlock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaPostBlockRepository extends JpaRepository<PostBlock, Integer> {
    void deleteByPost_PostId(Long postId);
    Boolean existsByPost_PostId(Long postId);
}
