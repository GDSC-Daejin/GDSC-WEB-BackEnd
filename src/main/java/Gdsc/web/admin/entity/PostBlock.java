package Gdsc.web.admin.entity;

import Gdsc.web.post.entity.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PostBlock {
    @Id
    @Column(name = "BLOCK_POST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postBlockId;

    @OneToOne
    @JoinColumn(name="POST_ID")
    private Post post;

    @CreationTimestamp
    @ApiModelProperty(example = "2022-01-06 14:57:42.777000")
    private LocalDateTime uploadDate;
}
