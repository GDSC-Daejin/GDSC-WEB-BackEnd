package Gdsc.web.repository.warnDescription;

import Gdsc.web.entity.Member;
import Gdsc.web.entity.WarnDescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaWarnDescription extends JpaRepository<WarnDescription, Integer> {

}
