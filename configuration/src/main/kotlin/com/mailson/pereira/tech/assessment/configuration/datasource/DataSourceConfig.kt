package com.mailson.pereira.tech.assessment.configuration.datasource

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

//@Configuration
class DataSourceConfig {

    //@Bean
    fun dataSource(): DataSource =
        DataSourceBuilder.create()
            .url(System.getenv("SPRING_DATASOURCE_URL"))
            .username(System.getenv("SPRING_DATASOURCE_USERNAME"))
            .password(System.getenv("SPRING_DATASOURCE_PASSWORD"))
            .driverClassName("org.postgresql.Driver")
            .build()
}
