package Gdsc.web.post.service;

import Gdsc.web.category.entity.Category;
import Gdsc.web.category.repository.JpaCategoryRepository;
import Gdsc.web.member.dto.MemberInfoResponseServerDto;
import Gdsc.web.member.service.MemberService;
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

import static Gdsc.web.post.service.PostService.getPostResponseDtos;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPostService {
    private final PostRepository postRepository;
    private final JpaCategoryRepository jpaCategoryRepository;
    private final MemberService memberService;
    // 추후 리팩 필요할듯
    public List<PostResponseDto> toPostResponseDto(List<Post> posts){
        return getPostResponseDtos(posts, memberService);
    }

    @Transactional
    public MemberInfoResponseServerDto findMemberInfo(String userId) {
        MemberInfoResponseServerDto member = memberService.getNicknameImage(userId);
        if (member == null) throw new IllegalArgumentException("없는 사용자 입니다.");
        return member;
    }
    @Transactional
    public Page<?> findAllMyTmpPostWithCategory(String username, String categoryName, Pageable pageable){
        Optional<Category> category = Optional.of(jpaCategoryRepository.findByCategoryName(categoryName).orElseThrow(
                ()-> new IllegalArgumentException("찾을 수 없는 카테고리 입니다.")));
        List<Post> posts = postRepository.findAllByTmpStoreIsTrueAndUserIdAndCategory(Post.class, username, category, pageable);
        return new PageImpl<>(toPostResponseDto(posts), pageable, posts.size());
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> findAllMyPost(String userId, final Pageable pageable){
        List<Post> posts =  postRepository.findByUserIdAndBlockedIsFalse(Post.class,userId, pageable);
        return new PageImpl<>(toPostResponseDto(posts), pageable, posts.size());
    }

    // 내 게시글 카테고리 별 조회
    @Transactional(readOnly = true)
    public Page<?> findAllMyPostWIthCategory(String userId, String categoryName, final Pageable pageable){
        Optional<Category> category = Optional.of(jpaCategoryRepository.findByCategoryName(categoryName).orElseThrow(
                ()-> new IllegalArgumentException("찾을 수 없는 카테고리 입니다.")));
        List<Post> posts = postRepository.findByUserIdAndCategoryAndBlockedIsFalse(Post.class,userId, category, pageable);
        return new PageImpl<>(toPostResponseDto(posts), pageable, posts.size());
    }

    @Transactional
    public Page<?> findAllMyTmpPosts(String userId, final Pageable pageable){
        List<Post> posts = postRepository.findAllByTmpStoreIsTrueAndUserId(Post.class, userId, pageable);
        return new PageImpl<>(toPostResponseDto(posts), pageable, posts.size());
    }
    @Transactional
    public PostResponseDto findMyPost(String userId, Long postId){
        Post post = postRepository.findByUserIdAndPostId(Post.class,userId, postId);
        return PostResponseDto.builder()
                .memberInfo(memberService.getNicknameImage(post.getUserId()))
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .userId(post.getUserId())
                .blocked(post.isBlocked())
                .postHashTags(post.getPostHashTags())
                .imagePath(post.getImagePath())
                .uploadDate(post.getUploadDate())
                .modifiedAt(post.getModifiedAt())
                .build();
    }

    public Page<?> findAllMyNotTmpPosts(String username, Pageable pageable) {
        List<Post> posts = postRepository.findAllByUserIdAndTmpStoreIsFalseAndBlockedIsFalse(Post.class, username, pageable);
        return new PageImpl<>(toPostResponseDto(posts), pageable, posts.size());
    }

    public Page<?> findAllMyNotTmpPostsWithCategory(String username, String categoryName, Pageable pageable) {
        Category category = jpaCategoryRepository.findByCategoryName(categoryName).orElseThrow(
                ()-> new IllegalArgumentException("찾을 수 없는 카테고리 입니다."));
        List<Post> posts = postRepository.findAllByUserIdAndBlockedIsFalseAndTmpStoreIsFalseAndCategory(username , category , pageable);
        return new PageImpl<>(toPostResponseDto(posts), pageable, posts.size());
    }

}
