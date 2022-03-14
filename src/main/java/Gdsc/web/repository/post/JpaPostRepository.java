package Gdsc.web.repository.post;

import Gdsc.web.entity.MemberInfo;
import Gdsc.web.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaPostRepository extends JpaRepository<Post,Integer> {
    @Override
    Optional<Post> findById(Integer integer);
    Optional<Post> findByPostId(Long postId);
    Optional<Post> findByPostIdAndMemberInfo(Long postId , MemberInfo memberInfo);
    List<Post> findByMemberInfo(MemberInfo memberInfo);
}
