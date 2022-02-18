package Gdsc.web.repository;

import Gdsc.web.domain.WarnDescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarnDescriptionRepository extends JpaRepository<WarnDescription, Integer> {
    Optional<WarnDescription> findByNickName(String nickName);
}