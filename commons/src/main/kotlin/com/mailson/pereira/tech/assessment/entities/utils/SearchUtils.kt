package com.mailson.pereira.tech.assessment.entities.utils

import com.mailson.pereira.tech.assessment.entities.dto.SearchParamDetail
import com.mailson.pereira.tech.assessment.entities.enums.ParamKeyEnum
import jakarta.servlet.http.HttpServletRequest
import java.math.BigDecimal

object SearchUtils {
	fun buildSearchParamDetails(
		restaurantName: String?,
		distance: Int?,
		customerRating: Int?,
		price: BigDecimal?,
		cuisineName: String?
	): List<SearchParamDetail> {
		val paramsList = mutableListOf<SearchParamDetail>()

		if (!restaurantName.isNullOrBlank()) {
			paramsList.add(
				SearchParamDetail(
					paramKey = ParamKeyEnum.RESTAURANT_NAME.paramName,
					paramValue = restaurantName,
					paramType = ParamKeyEnum.RESTAURANT_NAME.paramType.name
				)
			)
		}

		distance?.let {
			paramsList.add(
				SearchParamDetail(
					paramKey = ParamKeyEnum.DISTANCE.paramName,
					paramValue = it.toString(),
					paramType = ParamKeyEnum.DISTANCE.paramType.name
				)
			)
		}

		customerRating?.let {
			paramsList.add(
				SearchParamDetail(
					paramKey = ParamKeyEnum.CUSTOMER_RATING.paramName,
					paramValue = it.toString(),
					paramType = ParamKeyEnum.CUSTOMER_RATING.paramType.name
				)
			)
		}

		price?.let {
			paramsList.add(
				SearchParamDetail(
					paramKey = ParamKeyEnum.PRICE.paramName,
					paramValue = it.toString(),
					paramType = ParamKeyEnum.PRICE.paramType.name
				)
			)
		}

		if (!cuisineName.isNullOrBlank()) {
			paramsList.add(
				SearchParamDetail(
					paramKey = ParamKeyEnum.CUISINE_NAME.paramName,
					paramValue = cuisineName,
					paramType = ParamKeyEnum.CUISINE_NAME.paramType.name
				)
			)
		}

		return paramsList
	}

    fun extractClientIp(request: HttpServletRequest?): String {
        return if(request != null ) request.getHeader("X-Forwarded-For")?.split(",")?.firstOrNull()?.trim()
            ?: request.remoteAddr
        else ""
    }
}

