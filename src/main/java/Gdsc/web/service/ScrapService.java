package Gdsc.web.service;

import Gdsc.web.entity.MemberInfo;
import Gdsc.web.entity.MemberScrapPost;
import Gdsc.web.entity.Post;
import Gdsc.web.repository.member.JpaMemberRepository;
import Gdsc.web.repository.post.JpaPostRepository;
import Gdsc.web.repository.scrap.JpaScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScrapService {
    private final JpaScrapRepository jpaScrapRepository;
    private final JpaMemberRepository jpaMemberRepository;
    private final JpaPostRepository jpaPostRepository;

    public void scrap(String userId, Long postId){
        MemberScrapPost memberScrapPost = jpaScrapRepository.findByMemberInfo_Member_UserIdAndPost_PostId(userId, postId);
        if(memberScrapPost == null){
            Optional<Post> post = Optional.ofNullable(jpaPostRepository.findByPostId(postId).orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 포스트 입니다.")));
            MemberInfo memberInfo = jpaMemberRepository.findByUserId(userId).getMemberInfo();
            MemberScrapPost newMemberScrapPost = new MemberScrapPost();
            newMemberScrapPost.setMemberInfo(memberInfo);
            newMemberScrapPost.setPost(post.get());
            jpaScrapRepository.save(newMemberScrapPost);
        } else{
            jpaScrapRepository.delete(memberScrapPost);
        }
    }
}
