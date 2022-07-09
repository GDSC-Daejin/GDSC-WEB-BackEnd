package Gdsc.web.post.service;

import Gdsc.web.category.entity.Category;
import Gdsc.web.common.service.AwsS3FileUploadService;
import Gdsc.web.post.dto.PostRequestDto;
import Gdsc.web.post.dto.PostResponseDto;
import Gdsc.web.member.entity.Member;
import Gdsc.web.member.entity.MemberInfo;
import Gdsc.web.category.repository.JpaCategoryRepository;
import Gdsc.web.member.repository.MemberRepository;
import Gdsc.web.post.entity.Post;
import Gdsc.web.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final JpaCategoryRepository jpaCategoryRepository;

    private final AwsS3FileUploadService awsS3FileUploadService;

    private final Environment ev;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    @Value("${cloud.aws.s3.Url}")
    private String bucketUrl;
    @Transactional
    public void save(PostRequestDto requestDto , String userId) throws IOException {
        MemberInfo memberInfo = findMemberInfo(userId);
        Post post = new Post();
        post.setPostHashTags(requestDto.getPostHashTags());
        Category category = jpaCategoryRepository.findByCategoryName(requestDto.getCategory().getCategoryName())
                        .orElseThrow(()-> new IllegalArgumentException("찾을 수 없는 카테고리 입니다."));
        post.setCategory(category);
        post.setContent(requestDto.getContent());
        post.setTitle(requestDto.getTitle());
        post.setMemberInfo(memberInfo);
        post.setTmpStore(requestDto.getTmpStore());
        //json 형식 이미지나 , form-data 형식 이미지 둘중 하나만 들어왔을때!!
        if(requestDto.getThumbnail() != null ^ requestDto.getBase64Thumbnail() != null){
            if(!Objects.equals(requestDto.getBase64Thumbnail(), "")){
                post.setImagePath(awsS3FileUploadService.upload(requestDto, "static"));
            }
        }

        postRepository.save(post);
    }
    //수정
    @Transactional
    public void update(PostRequestDto requestDto, Long postId , String userId) throws IOException {
        MemberInfo memberInfo = findMemberInfo(userId);
        Post post = postRepository.findByPostIdAndMemberInfo(postId, memberInfo) //ㅣinteger가 아니라 long 타입이라 오류? jpa Long을 integer로 바꿔야 할까?
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));
        Category category = jpaCategoryRepository.findByCategoryName(requestDto.getCategory().getCategoryName())
                .orElseThrow(()-> new IllegalArgumentException("찾을 수 없는 카테고리 입니다."));
        post.setCategory(category);
        post.setPostHashTags(requestDto.getPostHashTags());
        post.setContent(requestDto.getContent());
        post.setTitle(requestDto.getTitle());
        post.setTmpStore(requestDto.getTmpStore());
        //json 형식 이미지나 , form-data 형식 이미지 둘중 하나만 들어왔을때!!
        if(requestDto.getThumbnail() != null ^ requestDto.getBase64Thumbnail() != null){
            if(!Objects.equals(requestDto.getBase64Thumbnail(), "")){
                // 원래 이미지가 있었을 때
                if(post.getImagePath() != null){
                    awsS3FileUploadService.fileDelete(post.getImagePath());
                }
                post.setImagePath(awsS3FileUploadService.upload(requestDto, ev.getActiveProfiles()[0]));
            }
        }else {
            // 원래 이미지가 있고 , 새로운 이미지가 없을 때
            if(post.getImagePath() != null){
                awsS3FileUploadService.fileDelete(post.getImagePath());
                post.setImagePath(null);
            }
        }


    }
    @Transactional
    public void deletePost(Long postId, String userId){
        MemberInfo memberInfo = findMemberInfo(userId);
        Post post = postRepository.findByPostIdAndMemberInfo(postId, memberInfo)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));
        if(post.getImagePath() != null){
            awsS3FileUploadService.fileDelete(post.getImagePath());
        }
        postRepository.delete(post);
    }


    //조회
    @Transactional(readOnly = true)
    public PostResponseDto findByPostIdAndBlockIsFalse(Long postId){
        Post post = postRepository.findByPostIdAndBlockedIsFalseAndTmpStoreIsFalse(postId,Post.class)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));
        return post.toPostResponseDto();
    }






    public List<PostResponseDto> toPostResponseDto(List<Post> posts){
        return posts.stream().map(Post::toPostResponseDto).collect(Collectors.toList());
    }
    @Transactional
    public MemberInfo findMemberInfo(String userId){
        Member member = memberRepository.findByUserId(userId);
        if(member == null) throw new IllegalArgumentException("없는 사용자 입니다.");
        return member.getMemberInfo();
    }
    // 내 게시글 조회

    // 모든 게시글 카테고리 별 조회
    @Transactional(readOnly = true)
    public Page<?> findPostAllWithCategory(String categoryName, final Pageable pageable){
        Optional<Category> category = Optional.of(jpaCategoryRepository.findByCategoryName(categoryName).orElseThrow(
                () -> new IllegalArgumentException("찾을 수 없는 카테고리 입니다.")));
        List<Post> posts = postRepository.findByCategoryAndTmpStoreIsFalseAndBlockedIsFalse(Post.class,category, pageable);
        return new PageImpl<>(toPostResponseDto(posts), pageable, posts.size());
    }
    // 모든 게시글 해시태그 별 조회
    @Transactional(readOnly = true)
    public Page<?> findPostAllWithPostHashTag(String tagName, final Pageable pageable){
        List<Post> posts = postRepository.findByPostHashTagsIsContainingOrContentIsContainingAndTmpStoreIsFalseAndBlockedIsFalse(Post.class,tagName ,tagName, pageable);
        return new PageImpl<>(toPostResponseDto(posts), pageable, posts.size());
    }

    //post 글 목록 불러오기
    @Transactional(readOnly = true)
    public Page<?> findPostAll(final Pageable pageable){
        List<Post> posts = postRepository.findAllByTmpStoreIsFalseAndBlockedIsFalse(Post.class, pageable);
        return new PageImpl<>(toPostResponseDto(posts), pageable, posts.size());
    }
    @Transactional
    public Page<?> findPostAllByTitle(String title, final Pageable pageable){
        List<Post> posts = postRepository.findAllByTitleContainingAndTmpStoreIsFalseAndBlockedIsFalse(Post.class, title, pageable);
        return new PageImpl<>(toPostResponseDto(posts), pageable, posts.size());
    }

    @Transactional
    public Page<?> findBockedPostAll(final Pageable pageable){
        List<Post> posts = postRepository.findAllByTmpStoreIsFalseAndBlockedIsTrue(Post.class, pageable);
        return new PageImpl<>(toPostResponseDto(posts), pageable, posts.size());
    }
    @Transactional
    public void updateView(Long postId){
        Optional<Post> post = postRepository.findByPostId(postId);
        post.get().setView(post.get().getView()+1);
    }


    // fulltext Search 검색
    @Transactional
    public Page<?> findFullTextSearch(String terms,Pageable pageable) {
        return postRepository.findAllByTitleLikeOrContentLikeOrPostHashTagsLikeAndTmpStoreIsFalseAndBlockedIsFalse(
                terms,pageable);
    }

}
