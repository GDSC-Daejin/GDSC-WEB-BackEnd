package Gdsc.web.elastic;

import Gdsc.web.entity.Post;
import com.beust.jcommander.internal.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface ElasticsearchRepository<T, ID> extends PagingAndSortingRepository<T, ID> {

}
