package Gdsc.web.image.entity;

import Gdsc.web.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "POST_INNER_IMAGE")
public class PostInnerImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_ID")
    private Long imageId;
    @Column(name = "POST_ID")
    private Long postId;
    private String imageUrl;
    @Column(name = "USER_ID")
    private String userId;
}
