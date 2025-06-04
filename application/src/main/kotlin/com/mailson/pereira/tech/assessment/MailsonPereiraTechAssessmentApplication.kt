package com.mailson.pereira.tech.assessment

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import java.util.*

@SpringBootApplication
@EntityScan("com.mailson.pereira.tech.assessment")
@OpenAPIDefinition(info=Info(
	title="Mailson Fernando Pereira - tech assessment",
	version = "v1",
	description = "Project for tech assessment"))
open class MailsonPereiraTechAssessmentApplication {
	companion object{
		@JvmStatic
		fun main(args: Array<String>) {
			TimeZone.setDefault((TimeZone.getTimeZone("GMT-3")))
			runApplication<com.mailson.pereira.tech.assessment.MailsonPereiraTechAssessmentApplication>(*args)
		}
	}
}
