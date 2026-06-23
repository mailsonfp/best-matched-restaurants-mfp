package com.mailson.pereira.tech.assessment.service.metric.maintenance

import com.google.gson.Gson
import com.mailson.pereira.tech.assessment.entities.extensions.resolveSearchTimestamp
import com.mailson.pereira.tech.assessment.entities.utils.SearchUtils
import com.mailson.pereira.tech.assessment.input.metric.maintenance.MetricMaintenanceInput
import com.mailson.pereira.tech.assessment.input.metric.maintenance.dto.SearchParamsMetricDTO
import com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantMatchedResponseInputDTO
import com.mailson.pereira.tech.assessment.output.message.producer.MessageOutputProducer
import com.mailson.pereira.tech.assessment.output.message.producer.dto.MessageDetailOutputDTO
import com.mailson.pereira.tech.assessment.output.message.producer.dto.MessageOutputDTO
import com.mailson.pereira.tech.assessment.service.restaurant.RestaurantSearchService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class MetricMaintenanceServiceImpl(
    private val restaurantSearchService: RestaurantSearchService,
    private val messageOutput: MessageOutputProducer,
    private val gson: Gson
): MetricMaintenanceInput {

    override fun generateMetricDataByAPI(
        searchParamsMetricDTO: SearchParamsMetricDTO,
        httpServletRequest: HttpServletRequest
    ) {
        val searchResult = restaurantSearchService.findBestMatchedRestaurants(
            searchParamsMetricDTO.restaurantName,
            searchParamsMetricDTO.distance,
            searchParamsMetricDTO.customerRating,
            searchParamsMetricDTO.price,
            searchParamsMetricDTO.cuisineName,
            httpServletRequest
        )

        sendSearchMetric(
            restaurantName = searchParamsMetricDTO.restaurantName,
            distance = searchParamsMetricDTO.distance,
            customerRating = searchParamsMetricDTO.customerRating,
            price = searchParamsMetricDTO.price,
            cuisineName = searchParamsMetricDTO.cuisineName,
            searchResult = searchResult,
            searchDate = searchParamsMetricDTO.searchDate,
            httpServletRequest
        )
    }

    private fun sendSearchMetric(
        restaurantName: String?,
        distance: Int?,
        customerRating: Int?,
        price: BigDecimal?,
        cuisineName: String?,
        searchResult: List<RestaurantMatchedResponseInputDTO>,
        searchDate: String?,
        httpServletRequest: HttpServletRequest?
    ) {

        val paramsList = SearchUtils.buildSearchParamDetails(
            restaurantName = restaurantName,
            distance = distance,
            customerRating = customerRating,
            price = price,
            cuisineName = cuisineName,
        ).map {
            MessageDetailOutputDTO(
                paramKey = it.paramKey,
                paramValue = it.paramValue,
                paramType = it.paramType
            )
        }

        val searchMetricMessage = MessageOutputDTO(
            searchTimestamp = searchDate.resolveSearchTimestamp(),
            searchClientIp = SearchUtils.extractClientIp(httpServletRequest),
            searchUserAgent = httpServletRequest?.getHeader("User-Agent"),
            searchReferrer = httpServletRequest?.getHeader("Referer"),
            searchResultCount = searchResult.size,
            searchOtherMetadata = gson.toJson(searchResult),
            searchParams = paramsList
        )

        messageOutput.sendMessageToSearchQueue(searchMetricMessage)
    }
}