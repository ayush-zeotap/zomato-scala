import org.scalatest._

class OrderTest extends FunSuite with BeforeAndAfter {
  var stats: OrderStatistics = _
  before {
    val orderReader = new OrderCSVReader("src/main/scala/zomato_cleaned.csv")
    stats = new OrderStatistics(orderReader)
  }

  test("Size of dataset") {
    assert(stats.order.length == 51717)
  }

  test("Parsed entries") {
    assert(stats.order(93) == Order(93,"Thanco's Natural Ice Creams","3.2/5",9,"Banashankari","Dessert Parlor","","Ice Cream","300"))
    assert(stats.order(51586) == Order(51586,"Quick Bites","NEW",0,"Whitefield","Quick Bites","","Chinese","200"))
  }
  test("No. of distinct locations") {
    assert(stats.noOfDistinctLocation() == 94)
  }

  test("No. of distinct cuisines at a certain location") {
    assert(stats.noOfDistinctCuisinesAtLocation("Whitefield") === 115)
  }

  test("Top N restaurants by Rating") {
    assert(stats.topNRestaurantsByRating(5) == List("Asia Kitchen By Mainland China", "Byg Brewski Brewing Company", "Sant Spa Cuisine", "Punjab Grill", "Belgian Waffle Factory"))
  }

  test("Top N restaurants by rating in a given location and restaurant type") {
    assert(stats.topNRestaurantsByLocation_Type("Brookefield", "Beverage Shop", 5) == List("Shake It Off", "Frozen Bottle", "Lassi Corner", "The Crunch"))
  }

  test("Top N restaurants by rating and least number of votes in a given location") {
    assert(stats.topNRestaurantsByVotes_Location("Whitefield", 5) == List("Creamiester", "Funjabi Times", "Bombay Kulfis", "Hotel North Indian Express", "Watheen Nuts"))
  }

}
