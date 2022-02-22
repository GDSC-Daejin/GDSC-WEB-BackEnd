package Gdsc.web.entity;

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
    @ManyToOne
    private Member fromUser;

    @ApiModelProperty(example = "누구한테")
    @ManyToOne
    @JoinColumn(name = "TO_USER")
    private Member toUser;



    @CreationTimestamp
    @ApiModelProperty(example = "2022-01-06 14:57:42.777000")
    private LocalDateTime uploadDate;







}
