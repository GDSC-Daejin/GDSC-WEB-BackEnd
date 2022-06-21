package Gdsc.web.dto.responseDto;

import Gdsc.web.entity.Post;
import lombok.Data;

import java.util.List;

@Data
public class ScrapPostResponseDto {
    List<PostResponseDto> postList;

    public ScrapPostResponseDto(List<Post> postList) {
        this.postList = postList.stream().map(Post::toPostResponseDto).collect(java.util.stream.Collectors.toList());
    }
}
