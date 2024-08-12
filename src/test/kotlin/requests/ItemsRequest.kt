package requests

data class ItemsRequest(
  val userId: Int,
  val title: String,
  val body: String
)
