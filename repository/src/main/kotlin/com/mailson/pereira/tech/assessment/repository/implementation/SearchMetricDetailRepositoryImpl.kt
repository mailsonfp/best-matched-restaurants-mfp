package com.mailson.pereira.tech.assessment.repository.implementation

import com.mailson.pereira.tech.assessment.output.metric.SearchMetricDetailRepository
import com.mailson.pereira.tech.assessment.output.metric.dto.SearchMetricDetailOutputDTO
import com.mailson.pereira.tech.assessment.repository.domain.toDTO
import com.mailson.pereira.tech.assessment.repository.domain.toEntity
import com.mailson.pereira.tech.assessment.repository.jpa.repositories.SearchMetricDetailJPARepository
import org.springframework.stereotype.Component

@Component
class SearchMetricDetailRepositoryImpl(
    private val searchMetricDetailJPARepository: SearchMetricDetailJPARepository
): SearchMetricDetailRepository {

    override fun save(searchMetricDetailOutputDTO: SearchMetricDetailOutputDTO): SearchMetricDetailOutputDTO {
        return searchMetricDetailJPARepository.saveAndFlush(searchMetricDetailOutputDTO.toEntity()).toDTO()
    }

    override fun saveList(searchMetricDetailList: List<SearchMetricDetailOutputDTO>): List<SearchMetricDetailOutputDTO> {
        return searchMetricDetailJPARepository.saveAll(
            searchMetricDetailList.map { it.toEntity() }
        ).map { it.toDTO() }
    }
}