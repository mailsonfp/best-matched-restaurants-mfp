package com.mailson.pereira.tech.assessment.service.metric.maintenance

import com.google.gson.Gson
import com.mailson.pereira.tech.assessment.entities.enums.ParamKeyEnum
import com.mailson.pereira.tech.assessment.input.metric.maintenance.MetricMaintenanceInput
import com.mailson.pereira.tech.assessment.input.metric.maintenance.dto.SearchParamsMetricDTO
import com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantMatchedResponseInputDTO
import com.mailson.pereira.tech.assessment.output.message.producer.MessageOutputProducer
import com.mailson.pereira.tech.assessment.output.message.producer.dto.MessageDetailOutputDTO
import com.mailson.pereira.tech.assessment.output.message.producer.dto.MessageOutputDTO
import com.mailson.pereira.tech.assessment.output.restaurant.RestaurantRepository
import com.mailson.pereira.tech.assessment.service.mapper.RestaurantMapper
import com.mailson.pereira.tech.assessment.service.restaurant.RestaurantSearchService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
class MetricMaintenanceServiceImpl(
    private val messageOutputProducer: MessageOutputProducer,
    private val restaurantRepository: RestaurantRepository,
    private val restaurantMapper: RestaurantMapper,
    private val gson: Gson
): MetricMaintenanceInput {

    override fun generateMetricDataByAPI(
        searchParamsMetricDTO: SearchParamsMetricDTO,
        httpServletRequest: HttpServletRequest
    ) {
        val matchedRestaurantsList = restaurantRepository.findBestMatchedRestaurants(
            searchParamsMetricDTO.restaurantName,
            searchParamsMetricDTO.distance,
            searchParamsMetricDTO.customerRating,
            searchParamsMetricDTO.price,
            searchParamsMetricDTO.cuisineName
        ).map { restaurantMapper.toMatchedDTO(it) }

        sendSearchMetric(
            searchParamsMetricDTO.restaurantName,
            searchParamsMetricDTO.distance,
            searchParamsMetricDTO.customerRating,
            searchParamsMetricDTO.price,
            searchParamsMetricDTO.cuisineName,
            matchedRestaurantsList,
            httpServletRequest,
            searchParamsMetricDTO.searchDate
        )
    }

    private fun sendSearchMetric(
        restaurantName: String?,
        distance: Int?,
        customerRating: Int?,
        price: BigDecimal?,
        cuisineName: String?,
        searchResult: List<RestaurantMatchedResponseInputDTO>,
        httpServletRequest: HttpServletRequest,
        searchDate: String?
    ) {
        val paramsList = arrayListOf<MessageDetailOutputDTO>()
        when {
            restaurantName != null -> paramsList.add(
                MessageDetailOutputDTO(
                    paramKey = ParamKeyEnum.RESTAURANT_NAME.paramName,
                    paramValue = restaurantName,
                    paramType = ParamKeyEnum.RESTAURANT_NAME.paramType.name,
                )
            )

            distance != null -> paramsList.add(
                MessageDetailOutputDTO(
                    paramKey = ParamKeyEnum.DISTANCE.paramName,
                    paramValue = distance.toString(),
                    paramType = ParamKeyEnum.DISTANCE.paramType.name,
                )
            )

            customerRating != null -> paramsList.add(
                MessageDetailOutputDTO(
                    paramKey = ParamKeyEnum.CUSTOMER_RATING.paramName,
                    paramValue = customerRating.toString(),
                    paramType = ParamKeyEnum.CUSTOMER_RATING.paramType.name,
                )
            )

            price != null -> paramsList.add(
                MessageDetailOutputDTO(
                    paramKey = ParamKeyEnum.PRICE.paramName,
                    paramValue = price.toString(),
                    paramType = ParamKeyEnum.PRICE.paramType.name,
                )
            )

            cuisineName != null -> paramsList.add(
                MessageDetailOutputDTO(
                    paramKey = ParamKeyEnum.CUISINE_NAME.paramName,
                    paramValue = cuisineName.toString(),
                    paramType = ParamKeyEnum.CUISINE_NAME.paramType.name,
                )
            )
        }

        val searchDateValue = searchDate?.let {
            LocalDate.parse(it,DateTimeFormatter.ofPattern("yyyy-MM-dd")).atTime(LocalTime.now())
        } ?: LocalDateTime.now()

        val searchMetricMessage = MessageOutputDTO(
            searchTimestamp = searchDateValue,
            searchClientIp = extractClientIp(httpServletRequest),
            searchUserAgent = httpServletRequest.getHeader("User-Agent"),
            searchReferrer = httpServletRequest.getHeader("Referer"),
            searchResultCount = searchResult.size,
            searchOtherMetadata = gson.toJson(searchResult),
            searchParams = paramsList
        )

        messageOutputProducer.sendMessageToSearchQueue(searchMetricMessage)
    }

    private fun extractClientIp(request: HttpServletRequest): String {
        return request.getHeader("X-Forwarded-For")?.split(",")?.firstOrNull()?.trim()
            ?: request.remoteAddr
    }

}