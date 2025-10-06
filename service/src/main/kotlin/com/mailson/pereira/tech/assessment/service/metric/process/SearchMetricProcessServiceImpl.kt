package com.mailson.pereira.tech.assessment.service.metric.process

import com.mailson.pereira.tech.assessment.entities.enums.ParamTypeEnum
import com.mailson.pereira.tech.assessment.input.message.consumer.metric.SearchMetricProcessService
import com.mailson.pereira.tech.assessment.input.message.consumer.metric.dto.MessageDetailInputDTO
import com.mailson.pereira.tech.assessment.input.message.consumer.metric.dto.MessageInputDTO
import com.mailson.pereira.tech.assessment.output.metric.SearchMetricDetailRepository
import com.mailson.pereira.tech.assessment.output.metric.SearchMetricRepository
import com.mailson.pereira.tech.assessment.output.metric.dto.SearchMetricDetailOutputDTO
import com.mailson.pereira.tech.assessment.output.metric.dto.SearchMetricOutputDTO
import com.mailson.pereira.tech.assessment.service.mapper.SearchMetricMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SearchMetricProcessServiceImpl(
    private val searchMetricRepository: SearchMetricRepository,
    private val searchMetricDetailRepository: SearchMetricDetailRepository,
    private val searchMetricMapper: SearchMetricMapper
): SearchMetricProcessService {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun processSearchMetric(searchMetricMessage: MessageInputDTO) {
        val searchMetric = searchMetricRepository.save(searchMetricMapper.toOutputDTO(searchMetricMessage))
        val searchMetricParams = arrayListOf<SearchMetricDetailOutputDTO>()
        searchMetricMessage.searchParams.forEach {
            searchMetricParams.add(
                searchMetricDetailRepository.save(
                    toSearchMetricDetailOutputDTO(searchMetric, it)
                )
            )
        }

        logger.info("method=processSearchMetric status=metricCreated searchParams=${searchMetricParams}")
    }

    private fun toSearchMetricDetailOutputDTO(
        searchMetric: SearchMetricOutputDTO,
        it: MessageDetailInputDTO
    ) = SearchMetricDetailOutputDTO(
        search = searchMetric,
        paramKey = it.paramKey,
        paramValue = it.paramValue,
        paramType = ParamTypeEnum.valueOf(it.paramType)
    )
}