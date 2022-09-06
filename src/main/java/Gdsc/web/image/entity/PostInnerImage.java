package Gdsc.web.image.entity;

import Gdsc.web.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PostInnerImage {
    @Id
    private Long imageId;
    @Column(name = "POST_ID")
    private Long postId;
    private String imageUrl;
    private String userId;
}
