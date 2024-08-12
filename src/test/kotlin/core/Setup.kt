package core

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.cdimascio.dotenv.dotenv
import io.qameta.allure.restassured.AllureRestAssured
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.config.LogConfig
import io.restassured.config.ObjectMapperConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.filter.log.LogDetail
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class Setup {

  val dotenv = dotenv()

  companion object {
    lateinit var requestSpecification: RequestSpecification
  }

  @BeforeAll
  fun setup() {
    val logConfig = LogConfig.logConfig()
      .enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL)
    val config = RestAssuredConfig.config().logConfig(logConfig)
      .objectMapperConfig(ObjectMapperConfig().jackson2ObjectMapperFactory { _, _ ->
        val om = ObjectMapper().findAndRegisterModules()
        om.configure(
          DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
          false
        )
        om
      })

    requestSpecification = RequestSpecBuilder()
      .setBaseUri(dotenv["uri"])
      .setContentType(ContentType.JSON)
      .setRelaxedHTTPSValidation()
      .setConfig(config)
      .addFilter(RequestLoggingFilter(LogDetail.ALL))
      .addFilter(ResponseLoggingFilter(LogDetail.ALL))
      .addFilter(/* filter = */ AllureRestAssured())
      .build()
  }

  @AfterAll
  fun tearDown() {
    RestAssured.reset()
  }
}
