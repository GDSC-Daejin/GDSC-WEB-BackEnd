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
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="userId")
    private Member member;

    @ManyToOne
    @JoinColumn(name="postId")
    private Post post;

    //@ManyToMany
    //두 테이블 사이의 pk만 가져와서 테이블을 생성하기 때문에 추가적 정보를 넣을 수 없음(사용빈도 낮음)
    //1:N 어노테이션 두 개를 이용해서 합해서, 중간 테이블을 만들어서 사용함


    @CreationTimestamp
    private LocalDateTime createDate;
}