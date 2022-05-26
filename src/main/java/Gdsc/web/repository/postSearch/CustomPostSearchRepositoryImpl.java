package Gdsc.web.repository.postSearch;

import Gdsc.web.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CustomPostSearchRepositoryImpl implements CustomPostSearchRepository{
    private final ElasticsearchOperations elasticsearchOperations;
    @Override
    public Page<Post> searchByTitle(String word, Pageable pageable) {
        Criteria criteria = Criteria.where("basicProfile.name").contains(word);
        Query query = new CriteriaQuery(criteria).setPageable(pageable);
        SearchHits<Post> search = elasticsearchOperations.search(query, Post.class);
        List<Post> posts =  search.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        return new PageImpl<>(posts,pageable, posts.size());

    }
}
