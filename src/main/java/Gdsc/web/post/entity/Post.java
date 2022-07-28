package Gdsc.web.post.entity;


import Gdsc.web.category.entity.Category;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;


import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Post {
    @Id
    @Column(name = "POST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(name = "USER_ID")
    private String userId; // 작성자 아이디

    @Column(length = 1000)
    String imagePath; // 썸네일
    @Column
    String title; // 제목
    @Lob
    String content; // 내용
    @Column(columnDefinition = "integer default 0", nullable = false , name = "VIEW_COUNT")
    private int view; //조회수





    //임시 저장 여부

    @NotNull
    @ColumnDefault("false")
    private boolean tmpStore;

    @OneToOne
    @JoinColumn
    private Category category;

    // casecade all = persist , remove !
    // casecade persist를 할경우 post entity에서 posthastage 를 데이터 베이스 저장을 할 수 있음
    // 물론 외부키값은 넣어줘야함!
    // ex PostHashTag postHashtag = new postHashtags();
    // postHashtag.setPost(post) 처럼
    @Column(name = "POST_HASH_TAGS")
    private String postHashTags;



    @Column(name = "MODIFIED_AT")
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @CreationTimestamp
    private LocalDateTime uploadDate;


    @Column(columnDefinition = "boolean default false")
    @NotNull
    private boolean blocked;

    @Builder
    public Post(String title , String content , String userId , boolean tmpStore , Category category , String postHashTags){
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.tmpStore = tmpStore;
        this.category =category;
        this.postHashTags = postHashTags;
    }





}
