package com.mailson.pereira.tech.assessment.repository.implementation

import com.mailson.pereira.tech.assessment.output.metric.SearchMetricRepository
import com.mailson.pereira.tech.assessment.output.metric.dto.SearchMetricOutputDTO
import com.mailson.pereira.tech.assessment.repository.domain.toDTO
import com.mailson.pereira.tech.assessment.repository.domain.toEntity
import com.mailson.pereira.tech.assessment.repository.jpa.repositories.SearchMetricJPARepository
import org.springframework.stereotype.Component

@Component
class SearchMetricRepositoryImpl(
    private val searchMetricJPARepository: SearchMetricJPARepository
): SearchMetricRepository {

    override fun save(searchMetricOutputDTO: SearchMetricOutputDTO): SearchMetricOutputDTO {
        return searchMetricJPARepository.save(searchMetricOutputDTO.toEntity()).toDTO()
    }
}