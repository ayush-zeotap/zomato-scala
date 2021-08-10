import org.scalatest._

class OrderTest extends FunSuite with BeforeAndAfter {
  var stats: OrderStatistics = _
  before {
    val orderReader = new OrderCSVReader("src/main/scala/dummy.csv")
    stats = new OrderStatistics(orderReader)
  }

  test("Size of dataset") {
    assert(stats.order.length == 9)
  }

  test("Parsed entries") {
    assert(stats.order(8) == Order(8,"Ruchi Maayaka","3.3/5",8,"Banashankari","Delivery","","South Indian","100"))
  }
  test("No. of distinct locations") {
    assert(stats.noOfDistinctLocation() == 3)
  }

  test("No. of distinct cuisines at a certain location") {
    assert(stats.noOfDistinctCuisinesAtLocation("Basavanagudi") == 2)
  }

  test("Top N restaurants by Rating") {
    assert(stats.topNRestaurantsByRating(5) == List("Spice Elephant", "Jalsa", "Timepass Dinner", "Grand Village", "San Churro Cafe"))
  }

  test("Top N restaurants by rating in a given location and restaurant type") {
    assert(stats.topNRestaurantsByLocation_Type("Banashankari", "Casual Dining", 5) == List("Spice Elephant", "Jalsa"))
  }

  test("Top N restaurants by rating and least number of votes in a given location") {
    assert(stats.topNRestaurantsByVotes_Location("Banashankari", 3) == List("Ruchi Maayaka", "Hide Out Cafe", "Addhuri Udupi Bhojana"))
  }

  test("No. of dishes liked in every restaurant") {
    assert(stats.noOfDishesLiked() == Map("Timepass Dinner" -> 7, "Ruchi Maayaka" -> 1, "Spice Elephant" -> 7, "Jalsa" -> 7, "Addhuri Udupi Bhojana" -> 1,
      "Rosewood International Hotel - Bar & Restaurant" -> 1, "Grand Village" -> 2, "San Churro Cafe" -> 7, "Hide Out Cafe" -> 1))
  }

  test("No. of distinct cuisines at each location") {
    assert(stats.noOfCuisinesAtAllLocations() == Map("Banashankari" -> 10, "Basavanagudi" -> 2, "Mysore Road" -> 4))
  }

  test("Count of restaurants for each cuisine type") {

    assert(stats.noOfRestaurantsForCuisine() == Map("Mexican" -> 1, "North Indian" -> 6, "Cafe" -> 2, "Andhra" -> 1, "Chinese" -> 3,
      "Italian" -> 1, "Rajasthani" -> 1, "South Indian" -> 3, "Mughlai" -> 1, "Thai" -> 1))
  }
}
