package com.mailson.pereira.tech.assessment.repository.implementation

import com.mailson.pereira.tech.assessment.entities.enums.ParamTypeEnum
import com.mailson.pereira.tech.assessment.entities.enums.SummarizeDataTypeEnum
import com.mailson.pereira.tech.assessment.entities.extensions.toLocalDateTimeWithPeriodTypeAndParamType
import com.mailson.pereira.tech.assessment.output.metric.SearchMetricReportRepository
import com.mailson.pereira.tech.assessment.output.metric.projection.AverageDataProjectionDTO
import com.mailson.pereira.tech.assessment.output.metric.projection.AverageMetricDetailDataProjectionDTO
import com.mailson.pereira.tech.assessment.output.metric.projection.TopParamKeyAndValueMetricDetailProjectionDTO
import com.mailson.pereira.tech.assessment.output.metric.projection.TopParamKeyMetricDetailProjectionDTO
import com.mailson.pereira.tech.assessment.repository.domain.SearchMetric
import com.mailson.pereira.tech.assessment.repository.domain.SearchMetricDetail
import com.mailson.pereira.tech.assessment.repository.jpa.specification.SearchMetricDetailSpecification
import com.mailson.pereira.tech.assessment.repository.jpa.specification.SearchMetricSpecification
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Root
import org.springframework.stereotype.Component
import java.math.BigDecimal
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

        val groupExpr: Expression<String> = SearchMetricSpecification.groupByPeriod(type)(cb, root)

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

    override fun averageNumericParamsByPeriod(
        type: SummarizeDataTypeEnum,
        start: String,
        end: String
    ): List<AverageMetricDetailDataProjectionDTO> {
        val cb = entityManager.criteriaBuilder
        val query = cb.createQuery(AverageMetricDetailDataProjectionDTO::class.java)
        val detailRoot = query.from(SearchMetricDetail::class.java)
        val metricJoin = detailRoot.join<SearchMetricDetail, SearchMetric>("search")

        val groupExpr = SearchMetricDetailSpecification.groupByPeriod(type)(cb, metricJoin.get("searchTimestamp"))

        query.select(
            cb.construct(
                AverageMetricDetailDataProjectionDTO::class.java,
                groupExpr,
                detailRoot.get<String>("paramKey"),
                cb.avg(cb.toBigDecimal(detailRoot.get("paramValue"))).`as`(BigDecimal::class.java)
            )
        )

        query.where(
            cb.and(
                cb.between(
                    metricJoin.get("searchTimestamp"),
                    start.toLocalDateTimeWithPeriodTypeAndParamType(type,true),
                    end.toLocalDateTimeWithPeriodTypeAndParamType(type, false)
                ),
                detailRoot.get<ParamTypeEnum>("paramType").`in`(ParamTypeEnum.INT, ParamTypeEnum.DECIMAL)
            )
        )

        query.groupBy(groupExpr, detailRoot.get<String>("paramKey"))
        query.orderBy(cb.asc(groupExpr))

        return entityManager.createQuery(query).resultList
    }

    override fun topParamKeyByPeriod(
        type: SummarizeDataTypeEnum,
        start: String,
        end: String
    ): List<TopParamKeyMetricDetailProjectionDTO> {
        val cb = entityManager.criteriaBuilder
        val query = cb.createQuery(TopParamKeyMetricDetailProjectionDTO::class.java)
        val detailRoot = query.from(SearchMetricDetail::class.java)
        val metricJoin = detailRoot.join<SearchMetricDetail, SearchMetric>("search")

        val groupExpr = SearchMetricDetailSpecification.groupByPeriod(type)(cb, metricJoin.get("searchTimestamp"))

        query.select(
            cb.construct(
                TopParamKeyMetricDetailProjectionDTO::class.java,
                groupExpr,
                detailRoot.get<String>("paramKey"),
                cb.count(detailRoot)
            )
        )

        query.where(cb.between(
            metricJoin.get("searchTimestamp"),
            start.toLocalDateTimeWithPeriodTypeAndParamType(type,true),
            end.toLocalDateTimeWithPeriodTypeAndParamType(type,false))
        )
        query.groupBy(groupExpr, detailRoot.get<String>("paramKey"))
        query.orderBy(cb.asc(groupExpr), cb.desc(cb.count(detailRoot)))

        return entityManager.createQuery(query).resultList
    }

    override fun topParamKeyValueByPeriod(
        type: SummarizeDataTypeEnum,
        start: String,
        end: String
    ): List<TopParamKeyAndValueMetricDetailProjectionDTO> {
        val cb = entityManager.criteriaBuilder
        val query = cb.createQuery(TopParamKeyAndValueMetricDetailProjectionDTO::class.java)
        val detailRoot = query.from(SearchMetricDetail::class.java)
        val metricJoin = detailRoot.join<SearchMetricDetail, SearchMetric>("search")

        val groupExpr = SearchMetricDetailSpecification.groupByPeriod(type)(cb, metricJoin.get("searchTimestamp"))

        query.select(
            cb.construct(
                TopParamKeyAndValueMetricDetailProjectionDTO::class.java,
                groupExpr,
                detailRoot.get<String>("paramKey"),
                detailRoot.get<String>("paramValue"),
                cb.count(detailRoot)
            )
        )

        query.where(cb.between(
            metricJoin.get("searchTimestamp"),
            start.toLocalDateTimeWithPeriodTypeAndParamType(type, true),
            end.toLocalDateTimeWithPeriodTypeAndParamType(type, false))
        )
        query.groupBy(groupExpr, detailRoot.get<String>("paramKey"), detailRoot.get<String>("paramValue"))
        query.orderBy(cb.asc(groupExpr), cb.desc(cb.count(detailRoot)))

        return entityManager.createQuery(query).resultList
    }
}