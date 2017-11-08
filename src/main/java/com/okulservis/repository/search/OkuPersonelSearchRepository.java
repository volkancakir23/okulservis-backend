package com.okulservis.repository.search;

import com.okulservis.domain.OkuPersonel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OkuPersonel entity.
 */
public interface OkuPersonelSearchRepository extends ElasticsearchRepository<OkuPersonel, Long> {
}
