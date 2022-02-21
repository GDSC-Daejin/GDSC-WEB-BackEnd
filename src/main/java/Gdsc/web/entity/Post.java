package Gdsc.web.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @Column(name = "POST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column // 썸네일
    @ApiModelProperty(example = "/ec2-south/~~~/")
    String imagePath;
    @Column
    @ApiModelProperty(example = "제목")
    String title;
    @Lob
    @ApiModelProperty(example = "내용")
    String content;


    @ManyToOne(optional = false , cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID" , nullable = false, unique = true)
    private MemberInfo memberInfo;

    //임시 저장 여부
    @Column
    @ApiModelProperty(example = "0")
    private boolean tmpStore;

    @JoinColumn
    @OneToMany(mappedBy = "post")
    private List<PostHashTag> postHashTags;

    @Column(name = "MODIFIED_AT")
    @NotNull
    private LocalDateTime modifiedAt;


    @CreationTimestamp
    @ApiModelProperty(example = "2022-01-06 14:57:42.777000")
    private LocalDateTime uploadDate;



}
