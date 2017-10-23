package com.okulservis.repository.search;

import com.okulservis.domain.OkuYolcu;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OkuYolcu entity.
 */
public interface OkuYolcuSearchRepository extends ElasticsearchRepository<OkuYolcu, Long> {
}
