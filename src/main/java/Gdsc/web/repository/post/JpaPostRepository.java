package Gdsc.web.repository.post;

import Gdsc.web.entity.Category;
import Gdsc.web.entity.MemberInfo;
import Gdsc.web.entity.Post;
import Gdsc.web.repository.scrap.T;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaPostRepository extends JpaRepository<Post,Integer> {

    Optional<Post> findByPostId(Long postId);
    <T> Optional<T> findByPostIdAndBlockedIsFalse(Long postId, Class<T> type);
    Optional<Post> findByPostIdAndMemberInfo(Long postId , MemberInfo memberInfo);
    <T> Page <T> findByMemberInfo(Class<T> tClass,MemberInfo memberInfo, Pageable pageable);
    <T> Page<T> findByMemberInfoAndCategoryIsFalse(Class<T> tClass,MemberInfo memberInfo, Optional<Category> category, Pageable pageable);

    <T> Page<T> findByCategoryAndTmpStoreIsFalseAndBlockedIsFalse(Class<T> tClass,Optional<Category> category, Pageable pageable);
    <T> Page<T> findAllByTmpStoreIsFalseAndBlockedIsFalse(Class<T> tClass,Pageable pageable);
    <T> Page<T> findByPostHashTagsIsContainingOrContentIsContainingAndTmpStoreIsFalseAndBlockedIsFalse(Class<T> tClass, String postHashTags,String content,Pageable pageable);
    <T> Page<T> findAllByTitleContainingAndTmpStoreIsFalseAndBlockedIsFalse(Class<T> tClass,String title,Pageable pageable);

    void deleteByPostIdAndAndMemberInfo(Long postId , MemberInfo memberInfo);
    void deleteByPostId(Long postId);
}
