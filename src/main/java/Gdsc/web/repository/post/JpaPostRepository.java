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
    Optional<Post> findByPostIdAndMemberInfo(Long postId , MemberInfo memberInfo);
    Page<Post> findByMemberInfo(MemberInfo memberInfo, Pageable pageable);
    <T> Page<T> findByMemberInfoAndCategory(Class<T> tClass,MemberInfo memberInfo, Optional<Category> category, Pageable pageable);
    <T> Page<T> findByCategoryAndTmpStoreIsFalse(Class<T> tClass,Optional<Category> category, Pageable pageable);
    <T> Page<T> findAllByTmpStoreIsFalse(Class<T> tClass,Pageable pageable);
    <T> Page<T> findByPostHashTagsIsContainingOrContentIsContainingAndTmpStoreIsFalse(Class<T> tClass, String postHashTags,String content,Pageable pageable);

    void deleteByPostIdAndAndMemberInfo(Long postId , MemberInfo memberInfo);
}
