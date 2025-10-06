package com.mailson.pereira.tech.assessment.repository.domain

import com.mailson.pereira.tech.assessment.entities.enums.ParamTypeEnum
import com.mailson.pereira.tech.assessment.output.metric.dto.SearchMetricDetailOutputDTO
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "search_metric_detail")
data class SearchMetricDetail(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "search_id")
    val search: SearchMetric,

    val paramKey: String,

    @Column(columnDefinition = "TEXT")
    val paramValue: String?,

    @Enumerated(EnumType.STRING)
    val paramType: ParamTypeEnum

)

fun SearchMetricDetail.toDTO() = SearchMetricDetailOutputDTO(
    id = this.id,
    search = this.search.toDTO(),
    paramKey = this.paramKey,
    paramValue = this.paramValue,
    paramType = this.paramType
)

fun SearchMetricDetailOutputDTO.toEntity() = SearchMetricDetail(
    id = this.id,
    search = this.search.toEntity(),
    paramKey = this.paramKey,
    paramValue = this.paramValue,
    paramType = this.paramType
)
