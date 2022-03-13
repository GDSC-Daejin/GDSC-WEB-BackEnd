package Gdsc.web.repository.postHashTag;

import Gdsc.web.entity.Post;
import Gdsc.web.entity.PostHashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPostHashTagRepository extends JpaRepository<PostHashTag , Integer> {
    void deletePostHashTagsByPost(Post post);
}
