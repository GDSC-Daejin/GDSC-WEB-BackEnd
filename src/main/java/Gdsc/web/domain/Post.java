package Gdsc.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
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
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @ApiModelProperty(example = "/ec2-south/~~~/")
    String imagePath;
    @Column
    @ApiModelProperty(example = "제목")
    String title;
    @Lob
    @ApiModelProperty(example = "내용")
    String content;

    @ApiModelProperty(example = "10101105445")
    @Column(name = "USER_ID")
    private String userId;

    @ColumnDefault("0")
    @ApiModelProperty(example = "0")
    private int likeNumber;

    @ColumnDefault("0")
    @ApiModelProperty(example = "0")
    private int unlikeNumber;


    /// 해쉬태그 구현 필요

    @Column
    @ApiModelProperty(example = "0")
    private boolean tmpStore;
    @Column(name = "MODIFIED_AT")
    @NotNull
    private LocalDateTime modifiedAt;


    @CreationTimestamp
    @ApiModelProperty(example = "2022-01-06 14:57:42.777000")
    private LocalDateTime uploadDate;

}
