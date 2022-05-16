package Gdsc.web.repository.post;

import Gdsc.web.dto.mapping.PostResponseMapping;
import Gdsc.web.entity.Category;
import Gdsc.web.entity.MemberInfo;
import Gdsc.web.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaPostRepository extends JpaRepository<Post,Integer> {

    Optional<Post> findByPostId(Long postId);
    <T> Optional<T> findByPostIdAndBlockedIsFalseAndTmpStoreIsFalse(Long postId, Class<T> type);
    Optional<Post> findByPostIdAndMemberInfo(Long postId , MemberInfo memberInfo);
    <T> Page <T> findByMemberInfo(Class<T> tClass,MemberInfo memberInfo, Pageable pageable);
    <T> Page<T> findByMemberInfoAndCategoryAndTmpStoreIsFalseAndBlockedIsFalse(Class<T> tClass,MemberInfo memberInfo, Optional<Category> category, Pageable pageable);
    <T> Page<T> findByMemberInfoAndTmpStoreIsFalseAndBlockedIsFalse(Class<T> tClass,MemberInfo memberInfo, Pageable pageable);
    <T> Page<T> findByCategoryAndTmpStoreIsFalseAndBlockedIsFalse(Class<T> tClass,Optional<Category> category, Pageable pageable);
    <T> Page<T> findAllByTmpStoreIsFalseAndBlockedIsFalse(Class<T> tClass,Pageable pageable);
    <T> Page<T> findByPostHashTagsIsContainingOrContentIsContainingAndTmpStoreIsFalseAndBlockedIsFalse(Class<T> tClass, String postHashTags,String content,Pageable pageable);
    <T> Page<T> findAllByTmpStoreIsFalseAndBlockedIsTrue(Class<T> tClass, Pageable pageable);
    <T> Page<T> findAllByTitleContainingAndTmpStoreIsFalseAndBlockedIsFalse(Class<T> tClass,String title,Pageable pageable);
     <T> Page<T> findAllByTmpStoreIsTrueAndMemberInfo(Class<T> tClass,MemberInfo memberInfo, Pageable pageable);
    <T> T findByMemberInfoAndTmpStoreIsTrueAndPostId(Class<T> tClass,MemberInfo memberInfo,Long postId);
    void deleteByPostIdAndAndMemberInfo(Long postId , MemberInfo memberInfo);
    void deleteByPostId(Long postId);
    <T> Page<T> findAllByTmpStoreIsTrueAndMemberInfoAndCategory(Class<PostResponseMapping> postResponseMappingClass, MemberInfo memberInfo, Optional<Category> category);
}
