package Gdsc.web.repository.post;

import Gdsc.web.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomizePostRepository {
    List<Post> fullTextSearch(String terms, int limit, int offset) throws InterruptedException;
}
