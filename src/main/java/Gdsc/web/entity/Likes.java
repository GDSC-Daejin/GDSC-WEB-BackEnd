package Gdsc.web.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Likes {
    // JPA 사용법 알아오기!
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIKE_ID")
    private int likeId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;



    @CreationTimestamp
    @ApiModelProperty(example = "2022-01-06 14:57:42.777000 ---Insert 시 자동 삽입 넣지말아요")
    private LocalDateTime uploadDate;
    //@ManyToMany
    //두 테이블 사이의 pk만 가져와서 테이블을 생성하기 때문에 추가적 정보를 넣을 수 없음(사용빈도 낮음)
    //1:N 어노테이션 두 개를 이용해서 합해서, 중간 테이블을 만들어서 사용함


}