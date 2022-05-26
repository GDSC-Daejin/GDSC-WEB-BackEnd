package Gdsc.web.config;


import Gdsc.web.repository.postSearch.PostSearchRepository;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchTimeoutException;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(basePackageClasses = PostSearchRepository.class)
@Configuration
public class elasticSearchConfig  extends AbstractElasticsearchConfiguration {
    @Value("${oci.es.url}")
    private String hostname; // localhost

    @Value("${oci.es.port}")
    private Integer port; // 9200


    @Override
    public RestHighLevelClient elasticsearchClient() throws DataAccessResourceFailureException {
        return new RestHighLevelClient(RestClient.builder(new HttpHost(hostname, port, "http")));
    }
}
