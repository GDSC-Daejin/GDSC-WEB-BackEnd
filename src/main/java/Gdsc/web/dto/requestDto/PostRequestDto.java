package Gdsc.web.dto.requestDto;

import Gdsc.web.entity.Category;
import Gdsc.web.entity.PostHashTag;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostRequestDto {
    @ApiModelProperty(example = "제목")
    private String title;
    @ApiModelProperty(example = "내용")
    private String content;
    @ApiModelProperty(example = "false")
    private boolean tmpStore;
    @ApiModelProperty(example = "Backend")
    private Category category;

    @ApiModelProperty(example = "HashtagContent")
    private List<PostHashTag> postHashTags;
    @ApiModelProperty(example = "폼타입으로 보낼때 이미지 주십쇼")
    private MultipartFile Thumbnail;
    @ApiModelProperty(example = "base64인코딩자료")
    private String base64Thumbnail;
    @ApiModelProperty(example = "base64인코딩해서보낼때 필요한 파일이름")
    private String fileName;
}
