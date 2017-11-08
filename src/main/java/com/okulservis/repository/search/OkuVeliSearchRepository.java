package com.okulservis.repository.search;

import com.okulservis.domain.OkuVeli;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OkuVeli entity.
 */
public interface OkuVeliSearchRepository extends ElasticsearchRepository<OkuVeli, Long> {
}
