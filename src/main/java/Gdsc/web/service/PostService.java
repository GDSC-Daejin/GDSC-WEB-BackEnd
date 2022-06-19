package Gdsc.web.service;

import Gdsc.web.dto.requestDto.PostRequestDto;
import Gdsc.web.dto.responseDto.PostResponseDto;
import Gdsc.web.entity.*;
import Gdsc.web.repository.category.JpaCategoryRepository;
import Gdsc.web.repository.member.MemberRepository;
import Gdsc.web.repository.post.PostRepository;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
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
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    @Value("${cloud.aws.s3.Url}")
    private String bucketUrl;
    @Transactional
    public void save(PostRequestDto requestDto , String userId) throws IOException {
        MemberInfo memberInfo = findMemberInfo(userId);
        Post post = new Post();
        post.setPostHashTags(requestDto.getPostHashTags());
        Optional<Category> category = jpaCategoryRepository.findByCategoryName(requestDto.getCategory().getCategoryName());
        post.setCategory(category.get());
        post.setContent(requestDto.getContent());
        post.setTitle(requestDto.getTitle());
        post.setMemberInfo(memberInfo);
        post.setTmpStore(requestDto.getTmpStore());
        //json 형식 이미지나 , form-data 형식 이미지 둘중 하나만 들어왔을때!!
        if(requestDto.getThumbnail() != null ^ requestDto.getBase64Thumbnail() != null){
            if(!Objects.equals(requestDto.getBase64Thumbnail(), "")){
                post.setImagePath(upload(requestDto, "static"));
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
        Category category = jpaCategoryRepository.findByCategoryName(requestDto.getCategory().getCategoryName()).get();
        post.setCategory(category);
        post.setPostHashTags(requestDto.getPostHashTags());
        post.setContent(requestDto.getContent());
        post.setTitle(requestDto.getTitle());
        post.setTmpStore(requestDto.getTmpStore());
        //json 형식 이미지나 , form-data 형식 이미지 둘중 하나만 들어왔을때!!
        if(requestDto.getThumbnail() != null ^ requestDto.getBase64Thumbnail() != null){
            if(!Objects.equals(requestDto.getBase64Thumbnail(), "")){
                fileDelete(post.getImagePath());
                post.setImagePath(upload(requestDto, "static"));
            }

        }


    }
    @Transactional
    public void deletePost(Long postId, String userId){
        MemberInfo memberInfo = findMemberInfo(userId);
        Optional<Post> post = postRepository.findByPostIdAndMemberInfo(postId, memberInfo);
        if(post.get().getImagePath() != null){
            fileDelete(post.get().getImagePath());
        }
        postRepository.delete(post.get());
    }


    //조회
    @Transactional(readOnly = true)
    public PostResponseDto findByPostIdAndBlockIsFalse(Long postId){
        Post post = postRepository.findByPostIdAndBlockedIsFalseAndTmpStoreIsFalse(postId,Post.class)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));
        return post.toPostResponseDto();
    }


    public String upload(PostRequestDto postRequestDto, String dirName) throws IOException {
        File uploadFile = convert(postRequestDto)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
        return upload(uploadFile, dirName);
    }

    // S3로 파일 업로드하기
    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();   // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }


    private Optional<File> convert(PostRequestDto postRequestDto) throws IOException {
        String serverPath = "tmp/";
        File convertFile;
        if(postRequestDto.getThumbnail() != null){
            convertFile = new File(serverPath + UUID.randomUUID()+postRequestDto.getThumbnail().getOriginalFilename());
        } else{
            convertFile = new File(serverPath + UUID.randomUUID()+postRequestDto.getFileName());
        }
        // grant write permission on linux
        Runtime.getRuntime().exec("chmod 777 " + convertFile.getAbsolutePath());
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            if(postRequestDto.getThumbnail() != null){ // form-data 형식으로 왔을 때
                try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                    fos.write(postRequestDto.getBase64Thumbnail().getBytes());
                }
            }
            else if(postRequestDto.getBase64Thumbnail() != null){ // json 으로 왔을 때
                try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                    Base64.Decoder decoder = Base64.getDecoder();
                    byte[] decodedBytes = decoder.decode(postRequestDto.getBase64Thumbnail().getBytes());
                    fos.write(decodedBytes);
                }
            }

            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    // delete s3에 올려진 사진
    public void fileDelete(String imageUrl) {
        imageUrl= imageUrl.replace(bucketUrl , "");
        try {
            log.info("imageUrl: " + (imageUrl).replace(File.separatorChar, '/'));
            amazonS3Client.deleteObject(bucket, (imageUrl).replace(File.separatorChar, '/'));
        } catch (AmazonServiceException e) {
            log.error(e.getErrorCode() + " : 버킷 이미지 삭제 실패 ");
        }
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
    @Transactional(readOnly = true)
    public Page<PostResponseDto> findMyPost(String userId, final Pageable pageable){
        MemberInfo memberInfo = findMemberInfo(userId);
        List<Post> posts =  postRepository.findByMemberInfoAndTmpStoreIsFalseAndBlockedIsFalse(Post.class,memberInfo, pageable);
        return new PageImpl<>(toPostResponseDto(posts), pageable, posts.size());
    }

    // 내 게시글 카테고리 별 조회
    @Transactional(readOnly = true)
    public Page<?> findMyPostWIthCategory(String userId, String categoryName, final Pageable pageable){
        MemberInfo memberInfo = findMemberInfo(userId);
        Optional<Category> category = Optional.of(jpaCategoryRepository.findByCategoryName(categoryName).orElseThrow(
                ()-> new IllegalArgumentException("찾을 수 없는 카테고리 입니다.")));
        List<Post> posts = postRepository.findByMemberInfoAndCategoryAndTmpStoreIsFalseAndBlockedIsFalse(Post.class,memberInfo, category, pageable);
        return new PageImpl<>(toPostResponseDto(posts), pageable, posts.size());
    }
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
    public Page<?> findAllMyTmpPost(String userId, final Pageable pageable){
        MemberInfo memberInfo = findMemberInfo(userId);
        List<Post> posts = postRepository.findAllByTmpStoreIsTrueAndMemberInfo(Post.class, memberInfo, pageable);
        return new PageImpl<>(toPostResponseDto(posts), pageable, posts.size());
    }
    @Transactional
    public PostResponseDto findMyTmpPost(String userId, Long postId){
        MemberInfo memberInfo = findMemberInfo(userId);
        return postRepository.findByMemberInfoAndTmpStoreIsTrueAndPostId(Post.class,memberInfo, postId).toPostResponseDto();

    }
    @Transactional
    public void updateView(Long postId){
        Optional<Post> post = postRepository.findByPostId(postId);
        post.get().setView(post.get().getView()+1);
    }

    @Transactional
    public Page<?> findAllMyTmpPostWithCategory(String username, String categoryName,Pageable pageable){
        MemberInfo memberInfo = findMemberInfo(username);
        Optional<Category> category = Optional.of(jpaCategoryRepository.findByCategoryName(categoryName).orElseThrow(
                ()-> new IllegalArgumentException("찾을 수 없는 카테고리 입니다.")));
        List<Post> posts = postRepository.findAllByTmpStoreIsTrueAndMemberInfoAndCategory(Post.class, memberInfo, category, pageable);
        return new PageImpl<>(toPostResponseDto(posts), pageable, posts.size());
    }
    // fulltext Search 검색
    @Transactional
    public Page<?> findFullTextSearch(String terms,Pageable pageable) {
        return postRepository.findAllByTitleLikeOrContentLikeOrPostHashTagsLikeAndTmpStoreIsFalseAndBlockedIsFalse(
                terms,pageable);
    }

}
