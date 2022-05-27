package Gdsc.web.repository.post;


import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;




@Repository
public class PostRepositoryImpl implements CustomizePostRepository{
    @PersistenceContext
    private EntityManager em;




}
