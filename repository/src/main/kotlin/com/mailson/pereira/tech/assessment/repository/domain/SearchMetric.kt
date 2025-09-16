package com.mailson.pereira.tech.assessment.repository.domain

import com.mailson.pereira.tech.assessment.output.metric.dto.SearchMetricOutputDTO
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime

@Entity
@Table(name = "search_metric")
data class SearchMetric(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ? = null,

    val searchDateTime: LocalDateTime = LocalDateTime.now(),

    val clientIp: String,

    val userAgent: String? = null,

    val referrer: String? = null,

    val resultCount: Int,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    val otherMetadata: String? = null,

    @OneToMany(mappedBy = "search", fetch = FetchType.LAZY)
    val parameters: List<SearchMetricDetail> = emptyList()
)

fun SearchMetric.toDTO() = SearchMetricOutputDTO (
    id = this.id,
    searchDateTime = this.searchDateTime,
    clientIp = this.clientIp,
    userAgent = this.userAgent,
    referrer = this.referrer,
    resultCount = this.resultCount,
    otherMetadata = this.otherMetadata,
)

fun SearchMetricOutputDTO.toEntity() = SearchMetric (
    id = this.id,
    searchDateTime = this.searchDateTime,
    clientIp = this.clientIp,
    userAgent = this.userAgent,
    referrer = this.referrer,
    resultCount = this.resultCount,
    otherMetadata = this.otherMetadata,
)