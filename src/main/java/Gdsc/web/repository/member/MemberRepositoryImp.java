package Gdsc.web.repository.member;

import Gdsc.web.repository.member.MemberRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
@Repository
public class MemberRepositoryImp implements MemberRepository {
    private final EntityManager em;

    public MemberRepositoryImp(EntityManager em) {
        this.em = em;
    }

    // 이 아래 작성
}
