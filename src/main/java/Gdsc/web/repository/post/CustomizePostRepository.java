package Gdsc.web.repository.post;

import Gdsc.web.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomizePostRepository {
    Page<Post> findAllByTitleLikeOrContentLikeOrPostHashTagsLikeAndTmpStoreIsFalseAndBlockedIsFalse( String word , Pageable pageable);

}
