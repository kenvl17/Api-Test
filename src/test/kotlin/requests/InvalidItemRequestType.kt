package requests

data class InvalidItemRequestType(
  val userId: String,
  val title: Int,
  val body: String
)
