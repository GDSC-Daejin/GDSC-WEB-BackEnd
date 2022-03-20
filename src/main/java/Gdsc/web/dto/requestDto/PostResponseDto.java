package Gdsc.web.dto.requestDto;

import Gdsc.web.entity.Category;
import Gdsc.web.entity.Likes;
import Gdsc.web.entity.PostHashTag;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
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
    private List<PostHashTag> postHashTags;

    private List<Likes> likes;
    private LocalDateTime modifiedAt;
    private LocalDateTime uploadDate;
}
