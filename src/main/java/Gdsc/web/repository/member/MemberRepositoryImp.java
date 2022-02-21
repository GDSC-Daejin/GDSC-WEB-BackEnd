package Gdsc.web.repository.member;

import Gdsc.web.repository.member.MemberRepository;

import javax.persistence.EntityManager;

public class MemberRepositoryImp implements MemberRepository {
    private final EntityManager em;

    public MemberRepositoryImp(EntityManager em) {
        this.em = em;
    }

    // 이 아래 작성
}
