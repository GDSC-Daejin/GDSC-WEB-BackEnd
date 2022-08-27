package Gdsc.web.post.repository;

import Gdsc.web.category.entity.Category;
import Gdsc.web.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Integer> , CustomizePostRepository {

    Optional<Post> findByPostId(Long postId);
    <T> Optional<T> findByPostIdAndBlockedIsFalseAndTmpStoreIsFalse(Long postId, Class<T> type);
    Optional<Post> findByPostIdAndUserId(Long postId , String userId);
    <T> List <T> findByUserId(Class<T> tClass, String userId, Pageable pageable);
    <T> List<T> findByUserIdAndCategoryAndBlockedIsFalse(Class<T> tClass,String userId, Optional<Category> category, Pageable pageable);
    <T> List<T> findByUserIdAndBlockedIsFalse(Class<T> tClass, String userId, Pageable pageable);
    <T> List<T> findByCategoryAndTmpStoreIsFalseAndBlockedIsFalse(Class<T> tClass,Optional<Category> category, Pageable pageable);
    <T> List<T> findAllByTmpStoreIsFalseAndBlockedIsFalse(Class<T> tClass,Pageable pageable);
    <T> List<T> findByPostHashTagsIsContainingOrContentIsContainingAndTmpStoreIsFalseAndBlockedIsFalse(Class<T> tClass, String postHashTags,String content,Pageable pageable);
    <T> List<T> findAllByTmpStoreIsFalseAndBlockedIsTrue(Class<T> tClass, Pageable pageable);
    <T> List<T> findAllByTitleContainingAndTmpStoreIsFalseAndBlockedIsFalse(Class<T> tClass,String title,Pageable pageable);
     <T> List<T> findAllByTmpStoreIsTrueAndUserId(Class<T> tClass, String userId, Pageable pageable);
    <T> T findByUserIdAndPostId(Class<T> tClass, String userId, Long postId);

    void deleteByPostId(Long postId);
    <T> List<T> findAllByTmpStoreIsTrueAndUserIdAndCategory(Class<T> tClass, String userId, Optional<Category> category , Pageable pageable);

    List<Post> findAllByUserIdAndTmpStoreIsFalseAndBlockedIsFalse(Class<Post> postClass, String userId, Pageable pageable);
    List<Post> findAllByUserIdAndBlockedIsFalseAndTmpStoreIsFalseAndCategory(String userId , Category category , Pageable pageable);

}
