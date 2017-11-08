package com.okulservis.repository.search;

import com.okulservis.domain.OkuSofor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OkuSofor entity.
 */
public interface OkuSoforSearchRepository extends ElasticsearchRepository<OkuSofor, Long> {
}
