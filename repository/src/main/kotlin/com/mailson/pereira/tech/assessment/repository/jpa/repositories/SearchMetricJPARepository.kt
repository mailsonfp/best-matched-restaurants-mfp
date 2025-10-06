package com.mailson.pereira.tech.assessment.repository.jpa.repositories

import com.mailson.pereira.tech.assessment.repository.domain.SearchMetric
import org.springframework.data.jpa.repository.JpaRepository

interface SearchMetricJPARepository: JpaRepository<SearchMetric,Long> {
}