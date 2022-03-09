package Gdsc.web.service;

import Gdsc.web.dto.PostResponseDto;
import Gdsc.web.dto.PostSaveRequestDto;
import Gdsc.web.dto.PostUpdateRequestDto;
import Gdsc.web.entity.Post;
import Gdsc.web.repository.post.JpaPostRepository;
import Gdsc.web.repository.post.PostRepositoryImp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final JpaPostRepository jpaPostRepository;
    private final PostRepositoryImp postRepositoryImp;

    //등록
    @Transactional
    public Long save(PostSaveRequestDto requestDto){
        return jpaPostRepository.save(requestDto.toEntity()).getPostId();
    }

    //수정
    @Transactional
    public Long update(Long postId, PostUpdateRequestDto requestDto){
        Post post = jpaPostRepository.findById(postId) //ㅣinteger가 아니라 long 타입이라 오류? jpa Long을 integer로 바꿔야 할까?
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));
        post.update(requestDto.getPostId(), requestDto.getTitle(), requestDto.getContent());
        return postId;
    }

    //조회
    public PostResponseDto findById(Long postId){
        Post entity = jpaPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));
        return new PostResponseDto(entity);
    }


}
