package com.okulservis.repository.search;

import com.okulservis.domain.OkuGuzergah;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OkuGuzergah entity.
 */
public interface OkuGuzergahSearchRepository extends ElasticsearchRepository<OkuGuzergah, Long> {
}
