package Gdsc.web.elastic;

import Gdsc.web.entity.Post;
import Gdsc.web.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.ElasticsearchClient;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ElasticService {
    private final ElasticsearchOperations elasticsearchOperations;
    private final PostRepository postRepository;
    public String createIndex() {
        IndexCoordinates indexCoordinates = elasticsearchOperations.getIndexCoordinatesFor(Post.class);
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(UUID.randomUUID().toString())
                .withObject(new Post())
                .build();

        return elasticsearchOperations.index(indexQuery, indexCoordinates);
    }
    public List<Post> insertDocument() {
        return (List<Post>) elasticsearchOperations.save(postRepository.findAll());
    }
}
