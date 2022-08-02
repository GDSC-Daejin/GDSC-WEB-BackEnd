package Gdsc.web.post.dto;

import Gdsc.web.category.entity.Category;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    @ApiModelProperty(example = "제목")
    private String title;
    @ApiModelProperty(example = "내용")
    private String content;
    @ApiModelProperty(example = "false")
    private boolean blocked;
    @ApiModelProperty(example = "Backend")
    private Category category;
    @ApiModelProperty(example = "false")
    private boolean tmpStore;
    @ApiModelProperty(example = "HashtagContent")
    private String postHashTags;
    @ApiModelProperty(example = "폼타입으로 보낼때 이미지 주십쇼")
    private MultipartFile Thumbnail;
    @ApiModelProperty(example = "base64인코딩자료")
    private String base64Thumbnail;
    @ApiModelProperty(example = "base64인코딩해서보낼때 필요한 파일이름")
    private String fileName;

    public boolean getTmpStore() {
        return this.tmpStore;
    }
}
