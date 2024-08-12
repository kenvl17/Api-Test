package requests

data class InvalidItemRequestField(
  val userId: Int,
  val title: String,
  val body: String,
  val additionalField :String
)
