package ro.ggabrielli.statistics.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ro.ggabrielli.statistics.domain.elasticsearch.ESModule;

/**
 * Created by gobi on 5/9/2018.
 */
public interface ESModuleRepository extends ElasticsearchRepository<ESModule, String>{
}
