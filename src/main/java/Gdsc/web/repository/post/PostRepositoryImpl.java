package Gdsc.web.repository.post;

import Gdsc.web.entity.Post;
import lombok.val;
import org.hibernate.Session;
import org.hibernate.search.engine.search.query.SearchResult;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;
import java.util.List;



@Repository
public class PostRepositoryImpl implements CustomizePostRepository{
    @PersistenceContext
    private EntityManager em;



    public void initiateIndexing() {

        SearchSession searchSession = Search.session(em);
        MassIndexer indexer = searchSession.massIndexer().idFetchSize(150).batchSizeToLoadObjects(25)
                .threadsToLoadObjects(12);

        try {

            indexer.startAndWait();

        } catch (InterruptedException e) {


            Thread.currentThread().interrupt();

        }

    }
    @SuppressWarnings("unchecked")
    @Override
    public  List<Post> fullTextSearch(String terms)  {
        System.out.println("test!!$@!$@!@");
        SearchResult<Post> result = Search.session(em)
                .search(Post.class)
                .where(f ->f.match()
                        .fields("title","content","postHashTags")
                        .matching(terms))
                .fetch(20);
        List<Post> hits = result.hits();
        long totalHitCount = result.total().hitCount();
        return hits;

    }
}
