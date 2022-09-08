package Gdsc.web.image.repository;

import Gdsc.web.image.entity.PostInnerImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostInnerImageRepository extends JpaRepository<PostInnerImage, Long> {

    List<PostInnerImage> findAllByPostId(Long postId);
}
