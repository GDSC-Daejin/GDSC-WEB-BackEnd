package Gdsc.web.repository.member;

import Gdsc.web.entity.Member;

import Gdsc.web.entity.Post;
import Gdsc.web.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaMemberRepository extends JpaRepository<Member, Integer> {
    //Optional<Member> findByEmail(String email);
    Optional<Member> findByUsername(String username);

    /*@Query("select m from Member m where m.role = 'MEMBER' or m.role = 'CORE' or m.role = 'LEAD'")
    List<Member> findMember();
    @Query("select m from Member m where m.role = 'GUEST'")
    List<Member> findGUEST();*/
    List<Member> findMembersByRoleInAndMemberInfo_PhoneNumberIsNotNull(@Param("role") List<RoleType> roleTypes);
    Member findByUserId(String id);
    Member findByEmail(String email);
    boolean existsByMemberInfo_Nickname(String nickname);
}
