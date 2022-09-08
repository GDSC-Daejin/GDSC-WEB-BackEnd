package Gdsc.web.image.dto;

import Gdsc.web.image.entity.PostInnerImage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ImageDto {
    private Long postId;
    private String image;
    private String fileName;
    public PostInnerImage toEntity() {
        return PostInnerImage.builder()
                .postId(postId)
                .imageUrl(image)
                .build();
    }
}
