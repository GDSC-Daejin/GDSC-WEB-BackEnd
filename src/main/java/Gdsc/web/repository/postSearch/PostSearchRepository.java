package Gdsc.web.repository.postSearch;

import Gdsc.web.elastic.ElasticsearchRepository;
import Gdsc.web.entity.Post;
import org.springframework.data.domain.Page;

public interface PostSearchRepository extends ElasticsearchRepository<Post,Integer> , CustomPostSearchRepository {
}
