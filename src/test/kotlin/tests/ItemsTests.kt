@file:Suppress("ClassName")

package tests

import core.Setup
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.ints.negative
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.qameta.allure.Description
import io.restassured.response.Response
import org.apache.http.HttpStatus
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import requests.InvalidItemRequestField
import requests.InvalidItemRequestType
import requests.ItemsEndpoints
import requests.ItemsRequest
import requests.ItemsResponse

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
open class ItemsTests : Setup() {
  private var request = ItemsEndpoints()
  private lateinit var response: Response
  private lateinit var token: String

  @Test
  @Description("Get All Items to list")
  open fun `Get All Items`() {
    val reqItem: List<ItemsResponse> = request.getAllItems(HttpStatus.SC_OK)
    reqItem.shouldHaveSize(100)
  }

  @Test
  open fun `Get Specific Item`() {
    val reqItem: ItemsResponse = request.getItem(1, HttpStatus.SC_OK)
    reqItem.id shouldBe 1
    reqItem.body shouldBe "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
    reqItem.userId shouldBe 1
    reqItem.title shouldBe "sunt aut facere repellat provident occaecati excepturi optio reprehenderit"
  }

  @Test
  open fun `Get Non Exist Items`() {
    val reqItem: ItemsResponse = request.getItem(1000, HttpStatus.SC_NOT_FOUND)
    reqItem.id shouldBe null
    reqItem.body shouldBe null
    reqItem.userId shouldBe null
    reqItem.title shouldBe null
  }

  @Test
  open fun `Check Items Structure`() {
    val reqItem: ItemsResponse = request.getItem(1, HttpStatus.SC_OK)
    reqItem.id.shouldBeInstanceOf<Int>()
    reqItem.userId.shouldBeInstanceOf<Int>()
    reqItem.title.shouldBeInstanceOf<String>()
    reqItem.body.shouldBeInstanceOf<String>()
  }

  @Test
  open fun `Create Items`() {
    val createItem = ItemsRequest(1, "test", "test")
    response = request.createItem(createItem, HttpStatus.SC_CREATED)
    val id: Int = response.jsonPath().getInt("id")
    val userId: Int = response.jsonPath().getInt("userId")
    val title: String = response.jsonPath().getString("title")
    val body: String = response.jsonPath().getString("body")
    id shouldBe 101
    userId shouldBe createItem.userId
    title shouldBe createItem.title
    body shouldBe createItem.body
  }

  @Test
  open fun `Create items with wrong data type`() {
    val createItem = ItemsRequest(1, "test", "test")
    response = request.createItem(createItem, HttpStatus.SC_CREATED)
    val id: Int = response.jsonPath().getInt("id")
    val userId: Int = response.jsonPath().getInt("userId")
    val title: String = response.jsonPath().getString("title")
    val body: String = response.jsonPath().getString("body")
    id shouldBe 101
    userId shouldBe createItem.userId
    title shouldBe createItem.title
    body shouldBe createItem.body
  }

  @Test
  open fun `Create items negative number`() {
    val createItem = ItemsRequest(-1, "test", "test")
    response = request.createItem(createItem, HttpStatus.SC_CREATED)
    val id: Int = response.jsonPath().getInt("id")
    val userId: Int = response.jsonPath().getInt("userId")
    val title: String = response.jsonPath().getString("title")
    val body: String = response.jsonPath().getString("body")
    id shouldBe 101
    userId shouldNotBe negative()
    title shouldBe createItem.title
    body shouldBe createItem.body
  }

  @Test
  open fun `Create items wrong datatype`() {
    val createItem = InvalidItemRequestType("1", 1, "test")
    response = request.createItem(createItem, HttpStatus.SC_CREATED)
    val id: Any = response.jsonPath().getInt("id")
    val userId: Any = response.jsonPath().getInt("userId")
    val title: Any = response.jsonPath().getString("title")
    val body: Any = response.jsonPath().getString("body")

    id shouldBeSameInstanceAs Int
    userId shouldBeSameInstanceAs Int
    title shouldBeSameInstanceAs String
    body shouldBeSameInstanceAs String
  }

  @Test
  open fun `Create items additional field`() {
    val createItem =
      InvalidItemRequestField(1, "test", "test", additionalField = "shouldNotBeIncluded")
    response = request.createItem(createItem, HttpStatus.SC_CREATED)
    val id: Int = response.jsonPath().getInt("id")
    val userId: Int = response.jsonPath().getInt("userId")
    val title: String = response.jsonPath().getString("title")
    val body: String = response.jsonPath().getString("body")
    val additionalField: String = response.jsonPath().getString("additionalField")
    id shouldBeSameInstanceAs Int
    userId shouldBeSameInstanceAs Int
    title shouldBeSameInstanceAs String
    body shouldBeSameInstanceAs String
    additionalField shouldBe null
  }
}






