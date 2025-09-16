package com.mailson.pereira.tech.assessment.repository.jpa.repositories

import com.mailson.pereira.tech.assessment.repository.domain.SearchMetric
import com.mailson.pereira.tech.assessment.repository.domain.SearchMetricDetail
import org.springframework.data.jpa.repository.JpaRepository

interface SearchMetricDetailJPARepository: JpaRepository<SearchMetricDetail,Long> {
}