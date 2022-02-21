package Gdsc.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostHashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_HASH_TAG_ID")
    private int post_HashTag_ID;

    @Column(name = "tag" , nullable = false)
    private String tag;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;


}
