package Gdsc.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {
    private Long postId;
    private String title;
    private String content;

    @Builder
    public PostUpdateRequestDto(Long postId, String title, String content){
        this.postId = postId;
        this.title = title;
        this.content = content;
    }
}
