class OrderStatistics(val orderReader: OrderReader) {
  val order = orderReader.readOrder()

  def topNRestaurantsByRating(N: Int) = {
    order.map(ord => (ord.restaurantName, parseFraction(ord.rating))).groupBy(_._1).toList.map(
      ord => (ord._1, ord._2.map(a => a._2).sum / ord._2.length)
    ).sortWith(_._2 > _._2).take(N).map(_._1)
  }

  def topNRestaurantsByLocation_Type(Location: String, Type: String, N: Int) = {
    order.filter(ord => ord.location == Location && ord.restaurantType == Type).map(ord => (ord.restaurantName, parseFraction(ord.rating))).groupBy(_._1).toList.map(
      ord => (ord._1, ord._2.map(a => a._2).sum / ord._2.length)
    ).sortWith(_._2 > _._2).take(N).map(_._1)
  }

  def parseFraction(ratio: String): Double = if (ratio.contains("/")) {
    val rat = ratio.split("/")
    rat(0).toDouble / rat(1).toDouble
  }
  else 0

  def topNRestaurantsByVotes_Location(Location: String, N: Int) = {
    order.filter(ord => ord.location == Location).map(ord => (ord.restaurantName, parseFraction(ord.rating), ord.numberOfVotes)).groupBy(_._1).toList.map(
      ord => (ord._1, ord._2.map(a => a._2).sum / ord._2.length, ord._2.map(a => a._3).sum)
    ).sortWith(_._2 > _._2).sortWith(_._3 < _._3).take(N).map(_._1)
  }

  def noOfDishesLiked(): Map[String, Int] = {
    order.map(ord => (ord.restaurantName, ord.dishesLiked)).groupBy(_._1).map {
      case (key, value) => (key, (value.map(dish => parseStringToItemsList(dish._2))).flatten.distinct.length)
    }
  }

  def noOfDistinctLocation(): Int = {
    order.map(ord => ord.location).distinct.length
  }

  def noOfDistinctCuisinesAtLocation(Location: String): Int = {
    order.filter(ord => ord.location == Location).map(ord => parseStringToItemsList(ord.typesOfCuisines)).flatten.distinct.length
  }

  def noOfCuisinesAtAllLocations(): Map[String, Int] = {
    order.map(ord => (ord.location, ord.typesOfCuisines)).groupBy(_._1).map {
      case (key, value) => (key, (value.map(cuisine => parseStringToItemsList(cuisine._2))).flatten.distinct.length)
    }
  }

  def parseStringToItemsList(items: String) = {
    items.replace("\"", "").split(",").toList
  }

  def noOfRestaurantsForCuisine() = {
    order.map(ord => (parseStringToItemsList(ord.typesOfCuisines), ord.restaurantName)).flatMap {
      case (k, v) => k.map(_ -> v)
    }.groupBy(_._1).map {
      case (k, v) => (k, v.length)
    }

  }
}
