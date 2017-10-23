package com.okulservis.repository.search;

import com.okulservis.domain.OkuSefer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OkuSefer entity.
 */
public interface OkuSeferSearchRepository extends ElasticsearchRepository<OkuSefer, Long> {
}
