package Gdsc.web.domain;

import io.swagger.annotations.ApiModelProperty;
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
public class WarnDescription {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @ApiModelProperty(example = "제목")
    String title;
    @Lob
    @ApiModelProperty(example = "내용")
    String content;
    @ApiModelProperty(example = "누가")
    @Column(name = "Username", length = 64 , nullable = false)
    String username;

    @ApiModelProperty(example = "누구한테")
    @Column(length = 64 , nullable = false)
    String nickName;

    @CreationTimestamp
    @ApiModelProperty(example = "2022-01-06 14:57:42.777000")
    private LocalDateTime uploadDate;
}