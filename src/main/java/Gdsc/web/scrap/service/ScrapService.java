package Gdsc.web.scrap.service;

import Gdsc.web.member.service.MemberService;
import Gdsc.web.post.dto.PostResponseDto;
import Gdsc.web.post.service.PostService;
import Gdsc.web.scrap.dto.ScrapPostList;
import Gdsc.web.scrap.mapper.MemberScrapPostResponseMapping;
import Gdsc.web.scrap.entity.MemberScrapPost;
import Gdsc.web.post.entity.Post;
import Gdsc.web.post.repository.PostRepository;
import Gdsc.web.scrap.repository.JpaScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScrapService {
    private final JpaScrapRepository jpaScrapRepository;
    private final MemberService memberService;
    private final PostRepository postRepository;
    private final PostService postService;

    public void scrap(String userId, Long postId){
        MemberScrapPost memberScrapPost = jpaScrapRepository.findByUserIdAndPost_PostId(userId, postId);
        if(memberScrapPost == null){
            Post post = postRepository.findByPostId(postId).orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 포스트 입니다."));
            MemberScrapPost newMemberScrapPost = MemberScrapPost.builder().userId(userId).post(post).build();
            jpaScrapRepository.save(newMemberScrapPost);
        } else{
            jpaScrapRepository.delete(memberScrapPost);
        }
    }



    @Transactional(readOnly = true)
    public Page<PostResponseDto> findMyScrapPost(String userId, final Pageable pageable){
        List<Post> scrap = jpaScrapRepository.findByUserId(userId).stream().map(MemberScrapPost::getPost).collect(Collectors.toList());
        return new PageImpl<>(postService.toPostResponseDto(scrap), pageable, scrap.size());
    }
    @Transactional(readOnly = true)
    public List<Long> findMyScrapPostList(String userId){
       return jpaScrapRepository.findByUserId(userId)
                .stream().map(it-> it.getPost().getPostId()).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public Page<?> findMyScrapPostByCategory(String username, String category, Pageable pageable) {
        List<Post> scrap = jpaScrapRepository.findByUserIdAndPost_Category_CategoryName(username, category).stream().map(MemberScrapPost::getPost).collect(Collectors.toList());
        return new PageImpl<>(postService.toPostResponseDto(scrap), pageable, scrap.size());
    }
}
