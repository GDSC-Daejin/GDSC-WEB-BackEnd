package Gdsc.web.config;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyLuceneAnalysisConfigurer implements LuceneAnalysisConfigurer {
    /**
     * @param context A context exposing methods to configure analysis.
     */
    @Override
    public void configure(LuceneAnalysisConfigurationContext context) {
        context.analyzer("custom_analyzer").custom().tokenizer(StandardTokenizerFactory.class)

                .tokenFilter(LowerCaseFilterFactory.class).tokenFilter(SnowballPorterFilterFactory.class)

                .param("language", "English").tokenFilter(ASCIIFoldingFilterFactory.class);


    }
}
