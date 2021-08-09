trait OrderReader {
  def readOrder(): Seq[Order]
}

case class Order(id: Int, restaurantName: String, rating: String, numberOfVotes: Int, location: String, restaurantType: String,
                 dishesLiked: String, typesOfCuisines: String, costForTwo: String)
