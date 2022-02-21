package Gdsc.web.repository.post;

import Gdsc.web.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPostRepository extends JpaRepository<Post,Integer> {
}
