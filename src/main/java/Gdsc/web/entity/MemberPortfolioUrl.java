package Gdsc.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MemberPortfolioUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Web_Url")
    private String webUrl;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private MemberInfo memberInfo;
}