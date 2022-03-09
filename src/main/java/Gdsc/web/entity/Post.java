package Gdsc.web.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class Post {
    @Id
    @Column(name = "POST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column
    @ApiModelProperty(example = "/ec2-south/~~~/")
    String imagePath; // 썸네일
    @Column
    @ApiModelProperty(example = "제목")
    String title; // 제목
    @Lob
    @ApiModelProperty(example = "내용")
    String content; // 내용


    @ManyToOne(optional = false , cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID" , nullable = false, unique = true)
    private MemberInfo memberInfo;

    //임시 저장 여부
    @Column
    @ApiModelProperty(example = "0")
    private boolean tmpStore;

    @ApiModelProperty(example = "누구한테")
    @OneToOne
    @JoinColumn
    private Category category;

    @JoinColumn(name = "POST_ID")
    @OneToMany
    private List<PostHashTag> postHashTags;

    @Column(name = "MODIFIED_AT")
    @LastModifiedDate

    private LocalDateTime modifiedAt;

    @CreationTimestamp
    @ApiModelProperty(example = "2022-01-06 14:57:42.777000 ---Insert 시 자동 삽입 넣지말아요")
    private LocalDateTime uploadDate;


}
