package Gdsc.web.post.repository;


import Gdsc.web.category.entity.Category;
import Gdsc.web.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class PostRepositoryImpl implements CustomizePostRepository{
    @PersistenceContext
    private EntityManager em;


    @Override
    public  List<Post> findAllByTitleLikeOrContentLikeOrPostHashTagsLikeAndTmpStoreIsFalseAndBlockedIsFalse(String word ,final Pageable pageable) {
        String jpql = "SELECT p FROM Post p WHERE (p.postHashTags LIKE :word " +
                "Or p.title LIKE :word " +
                "OR p.content LIKE :word )" +
                "AND p.blocked IS FALSE " +
                "AND p.tmpStore IS FALSE ";
        List<Post> resultList = em.createQuery(jpql, Post.class)
                .setParameter("word", "%" + word + "%")
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        // Dto 변환 필요
        return resultList;
    }

    @Override
    public List<Post> findAllByTitleLikeOrContentLikeOrPostHashTagsLikeAndCategoryAndTmpStoreIsFalseAndBlockedIsFalse(String word, Category category,final Pageable pageable) {
        String jpql = "SELECT p FROM Post p WHERE (p.postHashTags LIKE :word " +
                "OR p.title LIKE :word " +
                "OR p.content LIKE :word )" +
                "AND p.category = :category " +
                "AND p.blocked IS FALSE " +
                "AND p.tmpStore IS FALSE ";
        List<Post> resultList = em.createQuery(jpql, Post.class)
                .setParameter("word" , "%"+word+"%")
                .setParameter("category" , category)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        // Dto 변환 필요
        return resultList;
    }

}
