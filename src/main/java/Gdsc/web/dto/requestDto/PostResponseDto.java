package Gdsc.web.dto.requestDto;

import Gdsc.web.entity.Category;
import Gdsc.web.entity.Likes;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponseDto {
    @ApiModelProperty(example = "제목")
    private String title;
    @ApiModelProperty(example = "내용")
    private String content;
    @ApiModelProperty(example = "false")
    private boolean tmpStore;
    @ApiModelProperty(example = "Backend")
    private Category category;
    @ApiModelProperty(example = "HashtagContent")
    private String postHashTags;

    private List<Likes> likes;
    private LocalDateTime modifiedAt;
    private LocalDateTime uploadDate;
}
