package Gdsc.web.admin.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class WarnDescription {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Schema(description = "경고제목" , example = "스터디 미참여")
    String title;
    @Lob
    @Schema(description = "경고내용" , example = "스터디 미참여로 인한 경고")
    String content;

    @Column(name = "FROM_USER_ID")
    private String fromUser;

    @Column(name = "TO_USER_ID")
    private String toUser;



    @CreationTimestamp
    private LocalDateTime uploadDate;
}
