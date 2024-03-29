package Gdsc.web.post.service;

import Gdsc.web.category.entity.Category;
import Gdsc.web.common.service.AwsS3FileUploadService;
import Gdsc.web.image.entity.PostInnerImage;
import Gdsc.web.image.repository.PostInnerImageRepository;
import Gdsc.web.member.dto.MemberInfoResponseServerDto;
import Gdsc.web.member.service.MemberService;
import Gdsc.web.post.dto.PostRequestDto;
import Gdsc.web.post.dto.PostResponseDto;
import Gdsc.web.category.repository.JpaCategoryRepository;
import Gdsc.web.post.entity.Post;
import Gdsc.web.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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
    private final MemberService memberService;
    private final JpaCategoryRepository jpaCategoryRepository;
    private final PostInnerImageRepository postInnerImageRepository;

    private final AwsS3FileUploadService awsS3FileUploadService;

    private final Environment ev;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    @Value("${cloud.aws.s3.Url}")
    private String bucketUrl;

    /*public List<PostResponseDto> toPostResponseDto(List<Post> posts){
        return getPostResponseDtos(posts, memberService);
    }

    @NotNull
    static List<PostResponseDto> getPostResponseDtos(List<Post> posts, MemberService memberService) {
        List<MemberInfoResponseServerDto> memberInfoResponseServerDtos = memberService.getNicknameImages();
        return posts.stream()
                .map(post ->
                        PostResponseDto.builder()
                                .postId(post.getPostId())
                                .title(post.getTitle())
                                .content(post.getContent())
                                .category(post.getCategory())
                                .userId(post.getUserId())
                                .memberInfo(memberInfoResponseServerDtos.stream()
                                        .filter(memberInfoResponseServerDto -> memberInfoResponseServerDto.getUserId().equals(post.getUserId()))
                                        .findFirst()
                                        .orElse(null))
                                .blocked(post.isBlocked())
                                .postHashTags(post.getPostHashTags())
                                .imagePath(post.getImagePath())
                                .uploadDate(post.getUploadDate())
                                .modifiedAt(post.getModifiedAt())
                                .build())
                .collect(Collectors.toList());
    }*/
    @Transactional
    public Long save(PostRequestDto requestDto , String userId) throws IOException {
        Post post = new Post();
        post.setPostHashTags(requestDto.getPostHashTags());
        Category category = jpaCategoryRepository.findByCategoryName(requestDto.getCategory().getCategoryName())
                        .orElseThrow(()-> new IllegalArgumentException("찾을 수 없는 카테고리 입니다."));
        post.setCategory(category);
        post.setContent(requestDto.getContent());
        post.setTitle(requestDto.getTitle());
        post.setUserId(userId);
        post.setTmpStore(requestDto.getTmpStore());
        //json 형식 이미지나 , form-data 형식 이미지 둘중 하나만 들어왔을때!!
        if(requestDto.getThumbnail() != null ^ requestDto.getBase64Thumbnail() != null){
            if(!Objects.equals(requestDto.getBase64Thumbnail(), "")){
                post.setImagePath(awsS3FileUploadService.upload(requestDto, ev.getActiveProfiles()[0]));
            }
        }

        return postRepository.save(post).getPostId();
    }
    //수정
    @Transactional
    public void update(PostRequestDto requestDto, Long postId , String userId) throws IOException {
        Post post = postRepository.findByPostIdAndUserId(postId, userId) //ㅣinteger가 아니라 long 타입이라 오류? jpa Long을 integer로 바꿔야 할까?
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
        Post post = postRepository.findByPostIdAndUserId(postId, userId)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));
        if(post.getImagePath() != null){
            awsS3FileUploadService.fileDelete(post.getImagePath());
        }
        List<PostInnerImage> postInnerImages = postInnerImageRepository.findAllByPostId(postId);
        for(PostInnerImage postInnerImage : postInnerImages){
            awsS3FileUploadService.fileDelete(postInnerImage.getImageUrl());
        }
        postRepository.delete(post);
    }


    //조회
    @Transactional(readOnly = true)
    public Post findByPostIdAndBlockIsFalse(Long postId){
        return postRepository.findByPostIdAndBlockedIsFalseAndTmpStoreIsFalse(postId,Post.class)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));

    }









    // 내 게시글 조회

    // 모든 게시글 카테고리 별 조회
    @Transactional(readOnly = true)
    public List<Post> findPostAllWithCategory(String categoryName, final Pageable pageable){
        Optional<Category> category = Optional.of(jpaCategoryRepository.findByCategoryName(categoryName).orElseThrow(
                () -> new IllegalArgumentException("찾을 수 없는 카테고리 입니다.")));
        return postRepository.findByCategoryAndTmpStoreIsFalseAndBlockedIsFalse(Post.class,category, pageable);
    }
    // 모든 게시글 해시태그 별 조회
    @Transactional(readOnly = true)
    public List<Post> findPostAllWithPostHashTag(String tagName, final Pageable pageable){
        return postRepository.findByPostHashTagsIsContainingOrContentIsContainingAndTmpStoreIsFalseAndBlockedIsFalse(Post.class,tagName ,tagName, pageable);
    }

    //post 글 목록 불러오기
    @Transactional(readOnly = true)
    public List<Post>findPostAll(final Pageable pageable){
        return postRepository.findAllByTmpStoreIsFalseAndBlockedIsFalse(Post.class, pageable);
    }
    @Transactional
    public List<Post> findPostAllByTitle(String title, final Pageable pageable){
        return postRepository.findAllByTitleContainingAndTmpStoreIsFalseAndBlockedIsFalse(Post.class, title, pageable);
    }

    @Transactional
    public List<Post> findBockedPostAll(final Pageable pageable){
        return postRepository.findAllByTmpStoreIsFalseAndBlockedIsTrue(Post.class, pageable);

    }
    @Transactional
    public void updateView(Long postId){
        Optional<Post> post = postRepository.findByPostId(postId)
                .map(entity -> {
                    entity.setView(entity.getView() + 1);
                    return entity;
                });
    }


    // fulltext Search 검색
    @Transactional
    public List<Post> findFullTextSearch(String terms,Pageable pageable) {
        return postRepository.findAllByTitleLikeOrContentLikeOrPostHashTagsLikeAndTmpStoreIsFalseAndBlockedIsFalse(terms , pageable);
    }

    public List<Post> findFullTextSearchWithCategory(String word, String categoryName, Pageable pageable) {
        Category category = jpaCategoryRepository.findByCategoryName(categoryName).orElseThrow(
                () -> new IllegalArgumentException("찾을 수 없는 카테고리 입니다."));
        return postRepository.findAllByTitleLikeOrContentLikeOrPostHashTagsLikeAndCategoryAndTmpStoreIsFalseAndBlockedIsFalse(word, category, pageable);
    }
}
