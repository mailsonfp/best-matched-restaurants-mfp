package com.mailson.pereira.tech.assessment.service.metric.maintenance

import com.mailson.pereira.tech.assessment.input.metric.maintenance.MetricMaintenanceInput
import com.mailson.pereira.tech.assessment.input.metric.maintenance.dto.SearchParamsMetricDTO
import com.mailson.pereira.tech.assessment.service.restaurant.RestaurantSearchService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service

@Service
class MetricMaintenanceServiceImpl(
    private val restaurantSearchService: RestaurantSearchService
): MetricMaintenanceInput {

    override fun generateMetricDataByAPI(
        searchParamsMetricDTO: SearchParamsMetricDTO,
        httpServletRequest: HttpServletRequest
    ) {
        restaurantSearchService.findBestMatchedRestaurants(
            searchParamsMetricDTO.restaurantName,
            searchParamsMetricDTO.distance,
            searchParamsMetricDTO.customerRating,
            searchParamsMetricDTO.price,
            searchParamsMetricDTO.cuisineName,
            httpServletRequest
        )
    }
}