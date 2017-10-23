package com.okulservis.repository.search;

import com.okulservis.domain.OkuSehir;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OkuSehir entity.
 */
public interface OkuSehirSearchRepository extends ElasticsearchRepository<OkuSehir, Long> {
}
