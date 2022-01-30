package Gdsc.web.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class OnboardingMember {
    @Id
    @GeneratedValue
    private int id;

    @Column(length = 30)
    private String nickname;

    @Column(length = 30)
    private String major;
    @Column
    String email;// email
    @Column
    private String interest;

    @CreationTimestamp
    Timestamp timestamp;


}
