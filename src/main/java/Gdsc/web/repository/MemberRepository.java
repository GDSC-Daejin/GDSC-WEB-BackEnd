package Gdsc.web.repository;

import Gdsc.web.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    //Optional<Member> findByEmail(String email);
    Optional<Member> findByUsername(String username);

    @Query("select m from Member m where m.role = 'MEMBER' or m.role = 'CORE' or m.role = 'LEAD'")
    List<Member> findMember();

    @Query("select m from Member m where m.role = 'GUEST'")
    List<Member> findGUEST();


    Member findByUserId(String id);
    Member findByEmail(String email);

}
