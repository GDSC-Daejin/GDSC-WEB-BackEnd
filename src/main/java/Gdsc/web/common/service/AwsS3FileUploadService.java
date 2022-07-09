package Gdsc.web.common.service;

import Gdsc.web.post.dto.PostRequestDto;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AwsS3FileUploadService {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    @Value("${cloud.aws.s3.Url}")
    private String bucketUrl;


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
        convertFile.setReadable(true, true);
        convertFile.setWritable(true, true);
        convertFile.setExecutable(false, false);
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
    public void fileDelete(String fileUrl) {
        log.info("fileUrl: " + fileUrl);
        log.info("bucketUrl: " + bucketUrl);
        fileUrl= fileUrl.replace(bucketUrl , "");
        log.info("replace fileUrl: " + fileUrl);
        try {
            log.info("file Url Delete: " + (fileUrl).replace(File.separatorChar, '/'));
            amazonS3Client.deleteObject(bucket, (fileUrl).replace(File.separatorChar, '/'));
        } catch (AmazonServiceException e) {
            log.error(e.getErrorCode() + " : 버킷 파일 삭제 실패 ");
        }
    }
}
