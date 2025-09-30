package com.mailson.pereira.tech.assessment.repository.implementation

import com.mailson.pereira.tech.assessment.entities.enums.SummarizeDataTypeEnum
import com.mailson.pereira.tech.assessment.entities.extensions.toLocalDateTimeWithPeriodTypeAndParamType
import com.mailson.pereira.tech.assessment.output.metric.SearchMetricReportRepository
import com.mailson.pereira.tech.assessment.output.metric.projection.AverageDataProjectionDTO
import com.mailson.pereira.tech.assessment.repository.domain.SearchMetric
import com.mailson.pereira.tech.assessment.repository.jpa.specification.SearchMetricSpecifications
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Root
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class SearchMetricReportRepositoryImpl(
    @PersistenceContext
    private val entityManager: EntityManager
): SearchMetricReportRepository {

    override fun summarizeByPeriod(
        type: SummarizeDataTypeEnum,
        start: String,
        end: String
    ): List<AverageDataProjectionDTO> {
        val cb = entityManager.criteriaBuilder
        val query: CriteriaQuery<AverageDataProjectionDTO> = cb.createQuery(AverageDataProjectionDTO::class.java)
        val root: Root<SearchMetric> = query.from(SearchMetric::class.java)

        val groupExpr: Expression<String> = SearchMetricSpecifications.groupByPeriod(type)(cb, root)

        query.select(
            cb.construct(
                AverageDataProjectionDTO::class.java,
                groupExpr, // period
                cb.count(root), // total
                cb.sum(
                    cb.selectCase<Long>()
                        .`when`(cb.gt(root.get<Int>("resultCount"), 0), 1L)
                        .otherwise(0L)
                ), // withResult
                cb.sum(
                    cb.selectCase<Long>()
                        .`when`(cb.equal(root.get<Int>("resultCount"), 0), 1L)
                        .otherwise(0L)
                ) // withoutResult
            )
        )

        query.where(cb.between(
            root.get<LocalDateTime>("searchTimestamp"),
            start.toLocalDateTimeWithPeriodTypeAndParamType(type,true),
            end.toLocalDateTimeWithPeriodTypeAndParamType(type,false))
        )
        query.groupBy(groupExpr)
        query.orderBy(cb.asc(groupExpr))

        return entityManager.createQuery(query).resultList
    }
}