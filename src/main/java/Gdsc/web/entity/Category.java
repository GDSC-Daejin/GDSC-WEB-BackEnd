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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Category_ID")
    private int categoryId;

    @Column(name = "Category_Name" , length = 30 , nullable = false)
    private String categoryName;

    @Column(name = "MODIFIED_AT")
    @LastModifiedDate
    @NotNull
    private LocalDateTime modifiedAt;

    @CreationTimestamp
    @ApiModelProperty(example = "2022-01-06 14:57:42.777000 ---Insert 시 자동 삽입 넣지말아요")
    private LocalDateTime uploadDate;
}
