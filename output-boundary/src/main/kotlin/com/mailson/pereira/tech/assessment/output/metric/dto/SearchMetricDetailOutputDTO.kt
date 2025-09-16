package com.mailson.pereira.tech.assessment.output.metric.dto

import com.mailson.pereira.tech.assessment.entities.enums.ParamTypeEnum

data class SearchMetricDetailOutputDTO(
    val id: Long? = null,
    val search: SearchMetricOutputDTO,
    val paramKey: String,
    val paramValue: String?,
    val paramType: ParamTypeEnum
)
