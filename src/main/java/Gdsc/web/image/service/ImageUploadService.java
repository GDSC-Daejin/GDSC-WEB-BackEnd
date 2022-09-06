package Gdsc.web.image.service;

import Gdsc.web.common.service.AwsS3FileUploadService;
import Gdsc.web.image.dto.ImageDto;
import Gdsc.web.image.repository.PostInnerImageRepository;
import Gdsc.web.post.dto.PostRequestDto;
import Gdsc.web.post.entity.Post;
import Gdsc.web.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageUploadService {
    private final AwsS3FileUploadService awsS3FileUploadService;
    private final PostRepository postRepository;
    private final PostInnerImageRepository postInnerImageRepository;
    private final Environment ev;
    public ImageDto uploadImage(ImageDto requestImageDto , String userId) throws IOException {
        Post post = postRepository.findByPostId(requestImageDto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + requestImageDto.getPostId()));
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .base64Thumbnail(requestImageDto.getImage())
                .fileName(requestImageDto.getUserId())
                .build();
        String dir = ev.getActiveProfiles()[0] + "/" + userId + "/post/" + post.getPostId();
        ImageDto responseImageDto;
        try {
            String ImageUrl =  awsS3FileUploadService.upload(postRequestDto, dir);
             responseImageDto = ImageDto.builder()
                    .postId(post.getPostId())
                    .userId(userId)
                    .image(ImageUrl)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("이미지 업로드 실패");
            throw new IOException("이미지 업로드 실패");
        }
        postInnerImageRepository.save(responseImageDto.toEntity());
        log.info("이미지 업로드 성공");
        return responseImageDto;


    }

}
