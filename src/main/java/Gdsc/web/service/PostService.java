package Gdsc.web.service;

import Gdsc.web.dto.PostRequestDto;
import Gdsc.web.entity.Member;
import Gdsc.web.entity.MemberInfo;
import Gdsc.web.entity.Post;
import Gdsc.web.entity.PostHashTag;
import Gdsc.web.repository.member.JpaMemberRepository;
import Gdsc.web.repository.memberinfo.JpaMemberInfoRepository;
import Gdsc.web.repository.post.JpaPostRepository;
import Gdsc.web.repository.post.PostRepositoryImp;
import Gdsc.web.repository.postHashTag.JpaPostHashTagRepository;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final JpaPostRepository jpaPostRepository;
    private final JpaMemberRepository jpaMemberRepository;
    private final JpaPostHashTagRepository jpaPostHashTagRepository;
    private final PostRepositoryImp postRepositoryImp;

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    @Transactional
    public void save(PostRequestDto requestDto , String userId) throws IOException {
        MemberInfo memberInfo = findMemberInfo(userId);
        Post post = new Post();
        if(requestDto.getPostHashTags() != null){
            for(PostHashTag requestDto1 : requestDto.getPostHashTags()){
                requestDto1.setPost(post);
            }
            post.setPostHashTags(requestDto.getPostHashTags());
        }
        post.setCategory(requestDto.getCategory());
        post.setContent(requestDto.getContent());
        post.setTitle(requestDto.getTitle());
        post.setTmpStore(requestDto.isTmpStore());
        post.setMemberInfo(memberInfo);

        if(requestDto.getThumbnail() != null){
            post.setImagePath(upload(requestDto.getThumbnail() , "static"));
        }

        jpaPostRepository.save(post);
    }
    //수정
    @Transactional
    public void update(PostRequestDto requestDto, Long postId , String userId) throws IOException {
        MemberInfo memberInfo = findMemberInfo(userId);
        Post post = jpaPostRepository.findByPostIdAndMemberInfo(postId, memberInfo) //ㅣinteger가 아니라 long 타입이라 오류? jpa Long을 integer로 바꿔야 할까?
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));
        post.setCategory(requestDto.getCategory());

        jpaPostHashTagRepository.deletePostHashTagsByPost(post);
        for(PostHashTag requestDto1 : requestDto.getPostHashTags()){
            requestDto1.setPost(post);
        }

        post.setPostHashTags(requestDto.getPostHashTags());
        post.setContent(requestDto.getContent());
        post.setTitle(requestDto.getTitle());
        post.setTmpStore(requestDto.isTmpStore());
        if(requestDto.getThumbnail() != null){
            post.setImagePath(upload(requestDto.getThumbnail() , "static"));
        }
        //post.setImagePath(requestDto.getImagePath());

    }

    //조회
    @Transactional(readOnly = true)
    public Post findByPostId(Long postId){
        Post entity = jpaPostRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));
        return entity;
    }


    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
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

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(UUID.randomUUID()+file.getOriginalFilename());
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    // delete s3에 올려진 사진
    public void fileDelete(String imageUrl) {

        try {
            amazonS3Client.deleteObject(bucket, (imageUrl).replace(File.separatorChar, '/'));
        } catch (AmazonServiceException e) {
            log.error(e.getErrorCode() + " : 버킷 이미지 삭제 실패 ");
        }
    }

    public MemberInfo findMemberInfo(String userId){
        Member member = jpaMemberRepository.findByUserId(userId);
        if(member == null) throw new IllegalArgumentException("없는 사용자 입니다.");
        MemberInfo memberInfo = member.getMemberInfo();
        return memberInfo;
    }
    // 내 게시글 조회
    @Transactional(readOnly = true)
    public Page<Post> findMyPost(String userId, final Pageable pageable){
        MemberInfo memberInfo = findMemberInfo(userId);
        return jpaPostRepository.findByMemberInfo(memberInfo, pageable);
    }
}
