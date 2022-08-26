package Gdsc.web.post.service;

import Gdsc.web.category.entity.Category;
import Gdsc.web.category.repository.JpaCategoryRepository;
import Gdsc.web.member.service.MemberService;
import Gdsc.web.post.entity.Post;
import Gdsc.web.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OtherPostService {
    private final PostRepository postRepository;
    private final JpaCategoryRepository jpaCategoryRepository;
    private final PostService postService;
    @Transactional(readOnly = true)
    public Page<?> findAllNotTmpPosts(String userId, Pageable pageable) {
        List<Post> posts = postRepository.findAllByUserIdAndTmpStoreIsFalseAndBlockedIsFalse(Post.class, userId, pageable);
        return new PageImpl<>(postService.toPostResponseDto(posts), pageable, posts.size());
    }
    @Transactional(readOnly = true)
    public Page<?> findAllNotTmpPostsWithCategory(String userId, String categoryName, Pageable pageable) {
        Category category = jpaCategoryRepository.findByCategoryName(categoryName).orElseThrow(
                ()-> new IllegalArgumentException("찾을 수 없는 카테고리 입니다."));
        List<Post> posts = postRepository.findAllByUserIdAndBlockedIsFalseAndTmpStoreIsFalseAndCategory(userId, category, pageable);
        return new PageImpl<>(postService.toPostResponseDto(posts), pageable, posts.size());
    }
}
