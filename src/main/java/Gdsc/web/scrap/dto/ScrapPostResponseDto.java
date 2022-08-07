package Gdsc.web.scrap.dto;

import Gdsc.web.post.entity.Post;
import Gdsc.web.post.dto.PostResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class ScrapPostResponseDto {
    List<PostResponseDto> postList;

 /*   public ScrapPostResponseDto(List<Post> postList) {
        this.postList = postList.stream().map(Post::toPostResponseDto).collect(java.util.stream.Collectors.toList());
    }*/
}
