package Gdsc.web.service;

import Gdsc.web.dto.mapping.MemberScrapPostResponseMapping;
import Gdsc.web.entity.Member;
import Gdsc.web.entity.MemberInfo;
import Gdsc.web.entity.MemberScrapPost;
import Gdsc.web.entity.Post;
import Gdsc.web.repository.member.MemberRepository;
import Gdsc.web.repository.post.PostRepository;
import Gdsc.web.repository.scrap.JpaScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScrapService {
    private final JpaScrapRepository jpaScrapRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public void scrap(String userId, Long postId){
        MemberScrapPost memberScrapPost = jpaScrapRepository.findByMemberInfo_Member_UserIdAndPost_PostId(userId, postId);
        if(memberScrapPost == null){
            Optional<Post> post = Optional.ofNullable(postRepository.findByPostId(postId).orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 포스트 입니다.")));
            MemberInfo memberInfo = memberRepository.findByUserId(userId).getMemberInfo();
            MemberScrapPost newMemberScrapPost = new MemberScrapPost();
            newMemberScrapPost.setMemberInfo(memberInfo);
            newMemberScrapPost.setPost(post.get());
            jpaScrapRepository.save(newMemberScrapPost);
        } else{
            jpaScrapRepository.delete(memberScrapPost);
        }
    }

    public MemberInfo findMemberInfo(String userId){
        Member member = memberRepository.findByUserId(userId);
        if(member == null) throw new IllegalArgumentException("없는 사용자 입니다.");
        MemberInfo memberInfo = member.getMemberInfo();
        return memberInfo;
    }

    @Transactional(readOnly = true)
    public Page<MemberScrapPostResponseMapping> findMyScrapPost(String userId, final Pageable pageable){
        MemberInfo memberInfo = findMemberInfo(userId);
        return jpaScrapRepository.findByMemberInfo(memberInfo , pageable);
    }
}
