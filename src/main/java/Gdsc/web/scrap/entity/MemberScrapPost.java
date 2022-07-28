package Gdsc.web.scrap.entity;

import Gdsc.web.post.entity.Post;
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
public class MemberScrapPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID")
    private String userId;

    @ManyToOne
    @JoinColumn(name="POST_ID")
    private Post post;

    @CreationTimestamp
    private LocalDateTime uploadDate;


}
