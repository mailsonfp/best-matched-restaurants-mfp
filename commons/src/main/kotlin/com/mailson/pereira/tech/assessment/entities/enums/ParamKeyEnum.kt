package com.mailson.pereira.tech.assessment.entities.enums

enum class ParamKeyEnum(val paramName: String, paramType: ParamTypeEnum) {
    RESTAURANT_NAME("restaurantName", ParamTypeEnum.STRING),
    DISTANCE("distance", ParamTypeEnum.INT),
    CUSTOMER_RATING("customerRating", ParamTypeEnum.INT),
    PRICE("price", ParamTypeEnum.DECIMAL),
    CUISINE_NAME("cuisineName", ParamTypeEnum.STRING);
}