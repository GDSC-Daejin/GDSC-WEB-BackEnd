package Gdsc.web.service;

import Gdsc.web.entity.Likes;
import Gdsc.web.entity.MemberInfo;
import Gdsc.web.entity.Post;
import Gdsc.web.repository.like.JpaLikeRepository;
import Gdsc.web.repository.member.MemberRepository;
import Gdsc.web.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {
    private final JpaLikeRepository jpaLikeRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    public void like(String userId, Long postId){
        Likes likes = jpaLikeRepository.findByMemberInfo_Member_UserIdAndPost_PostId(userId , postId);
        if(likes == null){
            Optional<Post> post = Optional.ofNullable(postRepository.findByPostId(postId).orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 포스트 입니다.")));
            MemberInfo memberInfo = memberRepository.findByUserId(userId).getMemberInfo();
            Likes newLike = new Likes();
            newLike.setMemberInfo(memberInfo);
            newLike.setPost(post.get());
            jpaLikeRepository.save(newLike);
            log.info( userId + " like " + postId);
        }else {
            jpaLikeRepository.delete(likes);
            log.info( userId + " dislike " + postId);
        }

    }
}
