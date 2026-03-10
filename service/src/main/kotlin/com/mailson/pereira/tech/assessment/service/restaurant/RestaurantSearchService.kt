package com.mailson.pereira.tech.assessment.service.restaurant

import com.google.gson.Gson
import com.mailson.pereira.tech.assessment.entities.enums.ParamKeyEnum
import com.mailson.pereira.tech.assessment.input.exceptions.InvalidSearchParamsException
import com.mailson.pereira.tech.assessment.input.restaurant.RestaurantSearchInput
import com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantMatchedResponseInputDTO
import com.mailson.pereira.tech.assessment.output.message.producer.MessageOutputProducer
import com.mailson.pereira.tech.assessment.output.message.producer.dto.MessageDetailOutputDTO
import com.mailson.pereira.tech.assessment.output.message.producer.dto.MessageOutputDTO
import com.mailson.pereira.tech.assessment.output.restaurant.RestaurantRepository
import com.mailson.pereira.tech.assessment.service.mapper.RestaurantMapper
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class RestaurantSearchService(
    private val restaurantRepository: RestaurantRepository,
    private val restaurantMapper: RestaurantMapper,
    private val messageOutput: MessageOutputProducer,
    private val gson: Gson
): RestaurantSearchInput {

    override fun findBestMatchedRestaurants(
        restaurantName: String?,
        distance: Int?,
        customerRating: Int?,
        price: BigDecimal?,
        cuisineName: String?,
        httpServletRequest: HttpServletRequest?
    ): List<RestaurantMatchedResponseInputDTO> {
        validateRestaurantSearchParams(
            restaurantName,
            distance,
            customerRating,
            price,
            cuisineName)

        val matchedRestaurantsList = restaurantRepository.findBestMatchedRestaurants(
                restaurantName,
                distance,
                customerRating,
                price,
                cuisineName)
            .map { restaurantMapper.toMatchedDTO(it) }

        sendSearchMetric(
            restaurantName,
            distance,
            customerRating,
            price,
            cuisineName,
            matchedRestaurantsList,
            httpServletRequest
        )

        return matchedRestaurantsList
    }

    override fun validateRestaurantSearchParams(
        restaurantName: String?,
        distance: Int?,
        customerRating: Int?,
        price: BigDecimal?,
        cuisineName: String?
    ) {
        val errors = mutableListOf<String>()

        if(restaurantName == null && distance == null && customerRating == null
            && price == null && cuisineName == null)
            errors.add("At least one parameter have to be informed to fulfill the best matched restaurant search")

        customerRating?.let {
            if (it !in 1..5) errors.add("Customer Rating must be between 1 and 5 stars.")
        }

        distance?.let {
            if (it !in 1..10) errors.add("Distance must be between 1 and 10 miles.")
        }

        price?.let {
            if (it < BigDecimal(10) || it > BigDecimal(50)) {
                errors.add("Price must be between $10 and $50.")
            }
        }

        if (errors.isNotEmpty()) {
            throw InvalidSearchParamsException(errors.joinToString(";"))
        }
    }

    private fun sendSearchMetric(
        restaurantName: String?,
        distance: Int?,
        customerRating: Int?,
        price: BigDecimal?,
        cuisineName: String?,
        searchResult: List<RestaurantMatchedResponseInputDTO>,
        httpServletRequest: HttpServletRequest?
    ) {

        val paramsList = arrayListOf<MessageDetailOutputDTO>()

        if(!restaurantName.isNullOrBlank()) {
            paramsList.add(
                MessageDetailOutputDTO(
                    paramKey = ParamKeyEnum.RESTAURANT_NAME.paramName,
                    paramValue = restaurantName,
                    paramType = ParamKeyEnum.RESTAURANT_NAME.paramType.name,
                )
            )
        }

        distance?.let {
            paramsList.add(
                MessageDetailOutputDTO(
                    paramKey = ParamKeyEnum.DISTANCE.paramName,
                    paramValue = it.toString(),
                    paramType = ParamKeyEnum.DISTANCE.paramType.name,
                )
            )
        }

        customerRating?.let {
            paramsList.add(
                MessageDetailOutputDTO(
                    paramKey = ParamKeyEnum.CUSTOMER_RATING.paramName,
                    paramValue = customerRating.toString(),
                    paramType = ParamKeyEnum.CUSTOMER_RATING.paramType.name,
                )
            )
        }

        price?.let {
            paramsList.add(
                MessageDetailOutputDTO(
                    paramKey = ParamKeyEnum.PRICE.paramName,
                    paramValue = price.toString(),
                    paramType = ParamKeyEnum.PRICE.paramType.name,
                )
            )
        }

        if(!cuisineName.isNullOrBlank()) {
            paramsList.add(
                MessageDetailOutputDTO(
                    paramKey = ParamKeyEnum.CUISINE_NAME.paramName,
                    paramValue = cuisineName.toString(),
                    paramType = ParamKeyEnum.CUISINE_NAME.paramType.name,
                )
            )
        }

        val searchMetricMessage = MessageOutputDTO(
            searchClientIp = extractClientIp(httpServletRequest),
            searchUserAgent = httpServletRequest?.getHeader("User-Agent"),
            searchReferrer = httpServletRequest?.getHeader("Referer"),
            searchResultCount = searchResult.size,
            searchOtherMetadata = gson.toJson(searchResult),
            searchParams = paramsList
        )

        messageOutput.sendMessageToSearchQueue(searchMetricMessage)
    }

    private fun extractClientIp(request: HttpServletRequest?): String {
        return if(request != null ) request.getHeader("X-Forwarded-For")?.split(",")?.firstOrNull()?.trim()
            ?: request.remoteAddr
        else ""
    }
}