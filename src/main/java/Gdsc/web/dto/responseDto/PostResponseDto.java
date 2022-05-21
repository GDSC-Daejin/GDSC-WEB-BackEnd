package Gdsc.web.dto.responseDto;

import Gdsc.web.dto.mapping.PostResponseMapping;
import Gdsc.web.entity.Category;
import Gdsc.web.entity.Likes;
import Gdsc.web.entity.MemberInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private boolean tmpStore;
    private boolean blocked;
    private String imagePath;
    private Category category;
    private String postHashTags;
    private MemberInfo memberInfo;
    private LocalDateTime modifiedAt;
    private LocalDateTime uploadDate;
}
