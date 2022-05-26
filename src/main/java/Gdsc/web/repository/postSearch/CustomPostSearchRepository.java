package Gdsc.web.repository.postSearch;

import Gdsc.web.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomPostSearchRepository {
    Page<Post> searchByTitle(String word, Pageable pageable);
}
