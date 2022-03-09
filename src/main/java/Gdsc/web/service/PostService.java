package Gdsc.web.service;

import Gdsc.web.entity.Member;
import Gdsc.web.entity.MemberInfo;
import Gdsc.web.entity.Post;
import Gdsc.web.repository.member.JpaMemberRepository;
import Gdsc.web.repository.memberinfo.JpaMemberInfoRepository;
import Gdsc.web.repository.post.JpaPostRepository;
import Gdsc.web.repository.post.PostRepositoryImp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final JpaPostRepository jpaPostRepository;
    private final JpaMemberRepository jpaMemberRepository;
    private final JpaMemberInfoRepository jpaMemberInfoRepository;
    private final PostRepositoryImp postRepositoryImp;

    //등록
    @Transactional
    public void save(Post requestDto , String userId){
        MemberInfo memberInfo = findMemberInfo(userId).get();
        requestDto.setMemberInfo(memberInfo);
        jpaPostRepository.save(requestDto);
    }

    //수정
    @Transactional
    public void update(Post requestDto, Long postId , String userId){
        MemberInfo memberInfo = findMemberInfo(userId).get();
        Post post = jpaPostRepository.findByPostIdAndMemberInfo(postId, memberInfo) //ㅣinteger가 아니라 long 타입이라 오류? jpa Long을 integer로 바꿔야 할까?
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + requestDto.getPostId()));
        post.setCategory(requestDto.getCategory());
        post.setPostHashTags(requestDto.getPostHashTags());
        post.setContent(requestDto.getContent());
        post.setTitle(requestDto.getTitle());
        post.setTmpStore(requestDto.isTmpStore());
        //post.setImagePath(requestDto.getImagePath());

    }

    //조회
    public Post findByPostId(Long postId){
        Post entity = jpaPostRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));
        return entity;
    }

    public Optional<MemberInfo> findMemberInfo(String userId){
        Member member = jpaMemberRepository.findByUserId(userId);
        Optional<MemberInfo> memberInfo = Optional.ofNullable(jpaMemberInfoRepository.findByMember(member)
                .orElseThrow(
                () -> new IllegalArgumentException("없는 사용자 입니다" + userId)));
        return memberInfo;
    }


}
