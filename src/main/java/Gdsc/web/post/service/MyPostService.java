package Gdsc.web.post.service;

import Gdsc.web.category.entity.Category;
import Gdsc.web.category.repository.JpaCategoryRepository;
import Gdsc.web.member.entity.Member;
import Gdsc.web.member.entity.MemberInfo;
import Gdsc.web.member.repository.MemberRepository;
import Gdsc.web.post.dto.PostResponseDto;
import Gdsc.web.post.entity.Post;
import Gdsc.web.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPostService {
    private final PostRepository postRepository;
    private final JpaCategoryRepository jpaCategoryRepository;
    private final MemberRepository memberRepository;
    public List<PostResponseDto> toPostResponseDto(List<Post> posts){
        return posts.stream().map(Post::toPostResponseDto).collect(Collectors.toList());
    }

    @Transactional
    public MemberInfo findMemberInfo(String userId){
        Member member = memberRepository.findByUserId(userId);
        if(member == null) throw new IllegalArgumentException("없는 사용자 입니다.");
        return member.getMemberInfo();
    }
    @Transactional
    public Page<?> findAllMyTmpPostWithCategory(String username, String categoryName, Pageable pageable){
        MemberInfo memberInfo = findMemberInfo(username);
        Optional<Category> category = Optional.of(jpaCategoryRepository.findByCategoryName(categoryName).orElseThrow(
                ()-> new IllegalArgumentException("찾을 수 없는 카테고리 입니다.")));
        List<Post> posts = postRepository.findAllByTmpStoreIsTrueAndMemberInfoAndCategory(Post.class, memberInfo, category, pageable);
        return new PageImpl<>(toPostResponseDto(posts), pageable, posts.size());
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> findAllMyPost(String userId, final Pageable pageable){
        MemberInfo memberInfo = findMemberInfo(userId);
        List<Post> posts =  postRepository.findByMemberInfoAndBlockedIsFalse(Post.class,memberInfo, pageable);
        return new PageImpl<>(toPostResponseDto(posts), pageable, posts.size());
    }

    // 내 게시글 카테고리 별 조회
    @Transactional(readOnly = true)
    public Page<?> findAllMyPostWIthCategory(String userId, String categoryName, final Pageable pageable){
        MemberInfo memberInfo = findMemberInfo(userId);
        Optional<Category> category = Optional.of(jpaCategoryRepository.findByCategoryName(categoryName).orElseThrow(
                ()-> new IllegalArgumentException("찾을 수 없는 카테고리 입니다.")));
        List<Post> posts = postRepository.findByMemberInfoAndCategoryAndBlockedIsFalse(Post.class,memberInfo, category, pageable);
        return new PageImpl<>(toPostResponseDto(posts), pageable, posts.size());
    }

    @Transactional
    public Page<?> findAllMyTmpPosts(String userId, final Pageable pageable){
        MemberInfo memberInfo = findMemberInfo(userId);
        List<Post> posts = postRepository.findAllByTmpStoreIsTrueAndMemberInfo(Post.class, memberInfo, pageable);
        return new PageImpl<>(toPostResponseDto(posts), pageable, posts.size());
    }
    @Transactional
    public PostResponseDto findMyPost(String userId, Long postId){
        MemberInfo memberInfo = findMemberInfo(userId);
        return postRepository.findByMemberInfoAndPostId(Post.class,memberInfo, postId).toPostResponseDto();

    }

    public Page<?> findAllMyNotTmpPosts(String username, Pageable pageable) {
        MemberInfo memberInfo = findMemberInfo(username);
        List<Post> posts = postRepository.findAllByMemberInfoAndTmpStoreIsFalseAndBlockedIsFalse(Post.class, memberInfo, pageable);
        return new PageImpl<>(toPostResponseDto(posts), pageable, posts.size());
    }
}
