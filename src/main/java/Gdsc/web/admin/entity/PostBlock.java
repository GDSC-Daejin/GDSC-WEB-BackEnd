package Gdsc.web.admin.entity;

import Gdsc.web.post.entity.Post;
import lombok.*;
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
    private LocalDateTime uploadDate;
}
