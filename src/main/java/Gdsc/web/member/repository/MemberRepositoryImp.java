package Gdsc.web.member.repository;

import Gdsc.web.member.repository.CustomMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
@Repository
public class MemberRepositoryImp implements CustomMemberRepository {

    private EntityManager em;

    @Autowired
    public MemberRepositoryImp(EntityManager em) {
        this.em = em;
    }

    // 이 아래 작성
}
