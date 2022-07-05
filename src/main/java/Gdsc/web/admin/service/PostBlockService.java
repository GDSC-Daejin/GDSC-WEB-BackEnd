package Gdsc.web.admin.service;

import Gdsc.web.post.entity.Post;
import Gdsc.web.admin.entity.PostBlock;
import Gdsc.web.post.repository.PostRepository;
import Gdsc.web.admin.repository.JpaPostBlockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostBlockService {
    private final PostRepository postRepository;
    private final JpaPostBlockRepository jpaPostBlockRepository;

    @Transactional
    public void block(Long postId){
        Post post = postRepository.findByPostId(postId).orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 포스트 입니다."));
        if(jpaPostBlockRepository.existsByPost_PostId(postId)){
            jpaPostBlockRepository.deleteByPost_PostId(postId);
            post.setBlocked(false);
        }else{
            post.setBlocked(true);
            PostBlock postBlock = new PostBlock();
            postBlock.setPost(post);
            jpaPostBlockRepository.save(postBlock);
        }
    }
}
