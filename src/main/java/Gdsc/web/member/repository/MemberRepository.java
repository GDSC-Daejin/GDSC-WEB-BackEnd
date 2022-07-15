package Gdsc.web.member.repository;

import Gdsc.web.member.entity.Member;

import Gdsc.web.member.model.RoleType;
import Gdsc.web.member.repository.CustomMemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Integer> , CustomMemberRepository {
    //Optional<Member> findByEmail(String email);
    // Optional<Member> findByUsername(String username);

    /*@Query("select m from Member m where m.role = 'MEMBER' or m.role = 'CORE' or m.role = 'LEAD'")
    List<Member> findMember();
    @Query("select m from Member m where m.role = 'GUEST'")
    List<Member> findGUEST();*/
    List<Member> findMembersByRoleInAndMemberInfo_PhoneNumberIsNotNull(@Param("role") List<RoleType> roleTypes);
    Member findByUserId(String id);
    Member findByEmail(String email);
    boolean existsByMemberInfo_Nickname(String updateNickname);
    boolean existsByMemberInfo_NicknameAndMemberInfo_NicknameNot(String updateNickname , String nickname);
    boolean deleteByEmail(String s);
}
