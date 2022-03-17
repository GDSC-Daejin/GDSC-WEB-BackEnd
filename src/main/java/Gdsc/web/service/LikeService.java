package Gdsc.web.service;

import Gdsc.web.entity.Likes;
import Gdsc.web.entity.MemberInfo;
import Gdsc.web.entity.Post;
import Gdsc.web.repository.like.JpaLikeRepository;
import Gdsc.web.repository.member.JpaMemberRepository;
import Gdsc.web.repository.memberinfo.JpaMemberInfoRepository;
import Gdsc.web.repository.post.JpaPostRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final JpaLikeRepository jpaLikeRepository;
    private final JpaMemberRepository jpaMemberRepository;
    private final JpaPostRepository jpaPostRepository;
    public void like(String userId, Long postId){
        Likes likes = jpaLikeRepository.findByMemberInfo_MemberInfoIdAndPost_PostId(userId , postId);
        if(likes == null){
            Optional<Post> post = Optional.ofNullable(jpaPostRepository.findByPostId(postId).orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 포스트 입니다.")));
            MemberInfo memberInfo = jpaMemberRepository.findByUserId(userId).getMemberInfo();
            Likes newLike = new Likes();
            newLike.setMemberInfo(memberInfo);
            newLike.setPost(post.get());
            jpaLikeRepository.save(newLike);
        }else {
            jpaLikeRepository.delete(likes);
        }

    }
}
