package com.mailson.pereira.tech.assessment.service.mapper

import com.mailson.pereira.tech.assessment.entities.enums.ParamTypeEnum
import com.mailson.pereira.tech.assessment.input.message.consumer.metric.dto.MessageDetailInputDTO
import com.mailson.pereira.tech.assessment.input.message.consumer.metric.dto.MessageInputDTO
import com.mailson.pereira.tech.assessment.output.metric.dto.SearchMetricDetailOutputDTO
import com.mailson.pereira.tech.assessment.output.metric.dto.SearchMetricOutputDTO
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named

@Mapper(componentModel = "spring")
interface SearchMetricMapper {
    @Mapping(source = "searchTimestamp", target = "searchTimestamp")
    @Mapping(source = "searchClientIp", target = "clientIp")
    @Mapping(source = "searchUserAgent", target = "userAgent")
    @Mapping(source = "searchReferrer", target = "referrer")
    @Mapping(source = "searchResultCount", target = "resultCount")
    @Mapping(source = "searchOtherMetadata", target = "otherMetadata")
    fun toOutputDTO(dto: MessageInputDTO): SearchMetricOutputDTO

    @Mapping(source = "search", target = "search")
    @Mapping(source = "dto.paramKey", target = "paramKey")
    @Mapping(source = "dto.paramValue", target = "paramValue")
    @Mapping(source = "dto.paramType", target = "paramType")
    fun toDetailOutputDTO(dto: MessageDetailInputDTO, search: SearchMetricOutputDTO): SearchMetricDetailOutputDTO

    @Named("mapParamType")
    fun mapParamType(type: String): ParamTypeEnum = ParamTypeEnum.valueOf(type.uppercase())

}