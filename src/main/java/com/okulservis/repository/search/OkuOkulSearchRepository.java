package com.okulservis.repository.search;

import com.okulservis.domain.OkuOkul;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OkuOkul entity.
 */
public interface OkuOkulSearchRepository extends ElasticsearchRepository<OkuOkul, Long> {
}
