package Gdsc.web.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member_Scrap_post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="userId")
    private Member member;

    @ManyToOne
    @JoinColumn(name="postId")
    private Post post;
}
