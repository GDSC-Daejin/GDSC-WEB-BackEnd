package Gdsc.web.post.repository;

import Gdsc.web.category.entity.Category;
import Gdsc.web.member.entity.MemberInfo;
import Gdsc.web.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Integer> , CustomizePostRepository {

    Optional<Post> findByPostId(Long postId);
    <T> Optional<T> findByPostIdAndBlockedIsFalseAndTmpStoreIsFalse(Long postId, Class<T> type);
    Optional<Post> findByPostIdAndMemberInfo(Long postId , MemberInfo memberInfo);
    <T> List <T> findByMemberInfo(Class<T> tClass,MemberInfo memberInfo, Pageable pageable);
    <T> List<T> findByMemberInfoAndCategoryAndBlockedIsFalse(Class<T> tClass, MemberInfo memberInfo, Optional<Category> category, Pageable pageable);
    <T> List<T> findByMemberInfoAndBlockedIsFalse(Class<T> tClass, MemberInfo memberInfo, Pageable pageable);
    <T> List<T> findByCategoryAndTmpStoreIsFalseAndBlockedIsFalse(Class<T> tClass,Optional<Category> category, Pageable pageable);
    <T> List<T> findAllByTmpStoreIsFalseAndBlockedIsFalse(Class<T> tClass,Pageable pageable);
    <T> List<T> findByPostHashTagsIsContainingOrContentIsContainingAndTmpStoreIsFalseAndBlockedIsFalse(Class<T> tClass, String postHashTags,String content,Pageable pageable);
    <T> List<T> findAllByTmpStoreIsFalseAndBlockedIsTrue(Class<T> tClass, Pageable pageable);
    <T> List<T> findAllByTitleContainingAndTmpStoreIsFalseAndBlockedIsFalse(Class<T> tClass,String title,Pageable pageable);
     <T> List<T> findAllByTmpStoreIsTrueAndMemberInfo(Class<T> tClass,MemberInfo memberInfo, Pageable pageable);
    <T> T findByMemberInfoAndPostId(Class<T> tClass, MemberInfo memberInfo, Long postId);
    void deleteByPostIdAndAndMemberInfo(Long postId , MemberInfo memberInfo);
    void deleteByPostId(Long postId);
    <T> List<T> findAllByTmpStoreIsTrueAndMemberInfoAndCategory(Class<T> tClass, MemberInfo memberInfo, Optional<Category> category , Pageable pageable);
}
