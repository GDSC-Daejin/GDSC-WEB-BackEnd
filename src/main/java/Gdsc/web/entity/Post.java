package Gdsc.web.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Indexed;

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
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "postId")
@Document(indexName = "post_idx")
public class Post {
    @Id
    @org.springframework.data.annotation.Id // elastic
    @Column(name = "POST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Field(type = FieldType.Long)
    private Long postId;
    @Column
    @ApiModelProperty(example = "/ec2-south/~~~/")
    String imagePath; // 썸네일
    @Column
    @ApiModelProperty(example = "제목")
    @Field(type = FieldType.Keyword)
    String title; // 제목
    @Lob
    @ApiModelProperty(example = "내용")
    @Field(type = FieldType.Keyword)
    String content; // 내용
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view; //조회수



    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID" , nullable = false)
    private MemberInfo memberInfo;

    //임시 저장 여부
    @Column(columnDefinition = "boolean default false")
    @ApiModelProperty(example = "false")
    @NotNull
    @ColumnDefault("false")
    @Field(type = FieldType.Boolean)
    private boolean tmpStore;

    @ApiModelProperty(example = "Backend")
    @OneToOne
    @JoinColumn
    private Category category;

    // casecade all = persist , remove !
    // casecade persist를 할경우 post entity에서 posthastage 를 데이터 베이스 저장을 할 수 있음
    // 물론 외부키값은 넣어줘야함!
    // ex PostHashTag postHashtag = new postHashtags();
    // postHashtag.setPost(post) 처럼
    @Column(name = "POST_HASH_TAGS")
    @Field(type = FieldType.Keyword)
    private String postHashTags;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Likes> likes;

    @Column(name = "MODIFIED_AT")
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @CreationTimestamp
    @ApiModelProperty(example = "2022-01-06 14:57:42.777000 ---Insert 시 자동 삽입 넣지말아요")
    private LocalDateTime uploadDate;


    @Column(columnDefinition = "boolean default false")
    @ApiModelProperty(example = "false")
    @NotNull
    @Field(type = FieldType.Boolean)
    private boolean blocked;

    @Builder
    public Post(String title , String content , MemberInfo memberInfo , boolean tmpStore , Category category , String postHashTags){
        this.title = title;
        this.content = content;
        this.memberInfo = memberInfo;
        this.tmpStore = tmpStore;
        this.category =category;
        this.postHashTags = postHashTags;
    }


}
