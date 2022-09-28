package Gdsc.web.post.repository;

import Gdsc.web.category.entity.Category;
import Gdsc.web.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomizePostRepository {
    List<Post> findAllByTitleLikeOrContentLikeOrPostHashTagsLikeAndTmpStoreIsFalseAndBlockedIsFalse(String word);

    List<Post> findAllByTitleLikeOrContentLikeOrPostHashTagsLikeAndCategoryAndTmpStoreIsFalseAndBlockedIsFalse(String word, Category category , Pageable pageable);
}
