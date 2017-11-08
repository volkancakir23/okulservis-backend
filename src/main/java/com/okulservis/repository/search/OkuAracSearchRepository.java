package com.okulservis.repository.search;

import com.okulservis.domain.OkuArac;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OkuArac entity.
 */
public interface OkuAracSearchRepository extends ElasticsearchRepository<OkuArac, Long> {
}
