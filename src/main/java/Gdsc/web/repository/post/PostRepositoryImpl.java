package Gdsc.web.repository.post;

import Gdsc.web.entity.Post;
import org.hibernate.Session;
import org.hibernate.search.exception.SearchException;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;
import java.util.List;



@Repository
public class PostRepositoryImpl implements CustomizePostRepository{
    @PersistenceContext
    private EntityManager em;


    @SuppressWarnings("unchecked")
    @Override
    public  List<Post> fullTextSearch(String terms, int limit, int offset)  {
        System.out.println("test!!$@!$@!@");
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);

        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(Post.class).get();
        org.apache.lucene.search.Query luceneQuery = queryBuilder
                .keyword()
                .onFields("title","content","postHashTags")
                .matching(terms)
                .createQuery();
        System.out.println(luceneQuery.toString());
        // wrap Lucene query in a javax.persistence.Query
        FullTextQuery fullTextQuery  =
                fullTextEntityManager.createFullTextQuery(luceneQuery, Post.class);
        fullTextQuery.setMaxResults(limit);
        fullTextQuery.setFirstResult(offset);

        // execute search
        return fullTextQuery.getResultList();

    }
}
