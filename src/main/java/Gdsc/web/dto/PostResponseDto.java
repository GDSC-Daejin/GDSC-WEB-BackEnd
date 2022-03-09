package Gdsc.web.dto;

import Gdsc.web.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private String writer;

    public PostResponseDto(Post entity){
        this.postId = entity.getPostId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.writer = entity.getWriter();
    }
}
