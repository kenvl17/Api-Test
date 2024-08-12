package requests

import com.fasterxml.jackson.databind.ObjectMapper
import core.Setup
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response

open class ItemsEndpoints : Setup() {
  private var objectMapper: ObjectMapper = ObjectMapper()

  open fun createItem(body: Any, status: Int): Response {
    val response = Given {
      spec(requestSpecification)
        .body(
          objectMapper.writeValueAsString(body)
        )
    } When {
      post("/posts")
    } Then {
      statusCode(status)
    } Extract {
      response()
    }
    return response
  }

  open fun getAllItems(status: Int): List<ItemsResponse> {
    val response = Given {
      spec(requestSpecification)
    } When {
      get("/posts")
    } Then {
      statusCode(status)
    } Extract {
      `as`(Array<ItemsResponse>::class.java).toList()
    }
    return response
  }

  open fun getItem(userId: Int, status: Int): ItemsResponse {
    val response = Given {
      spec(requestSpecification)
    } When {
      get("/posts/$userId")
    } Then {
      statusCode(status)
    } Extract {
      `as`(ItemsResponse::class.java)
    }
    return response
  }
}

