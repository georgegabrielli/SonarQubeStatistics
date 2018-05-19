package ro.ggabrielli.statistics.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ro.ggabrielli.statistics.domain.elasticsearch.ESModule;

public interface ESModuleRepository extends ElasticsearchRepository<ESModule, String>{
}
