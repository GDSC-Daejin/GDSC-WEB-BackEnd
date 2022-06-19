package Gdsc.web.service;

import Gdsc.web.entity.Post;
import Gdsc.web.entity.PostBlock;
import Gdsc.web.repository.post.JpaPostRepository;
import Gdsc.web.repository.postBlock.JpaPostBlockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostBlockService {
    private final JpaPostRepository jpaPostRepository;
    private final JpaPostBlockRepository jpaPostBlockRepository;

    @Transactional
    public void block(Long postId){
        Optional<Post> post = Optional.ofNullable(jpaPostRepository.findByPostId(postId).orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 포스트 입니다.")));
        if(jpaPostBlockRepository.existsByPost_PostId(postId)){
            jpaPostBlockRepository.deleteByPost_PostId(postId);
            post.get().setBlocked(false);
        }else{
            post.get().setBlocked(true);
            PostBlock postBlock = new PostBlock();
            postBlock.setPost(post.get());
            jpaPostBlockRepository.save(postBlock);
        }
    }
}
