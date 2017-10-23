package com.okulservis.repository.search;

import com.okulservis.domain.OkuOgrenci;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OkuOgrenci entity.
 */
public interface OkuOgrenciSearchRepository extends ElasticsearchRepository<OkuOgrenci, Long> {
}
