import scala.io.Source

class OrderCSVReader(val filename: String) extends OrderReader {
  override def readOrder(): Seq[Order] = {
    for {
      line <- Source.fromFile(filename).getLines().toList
      values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1).map(_.trim)
    }
    yield Order(values(0).toInt, values(1), values(2), values(3).toInt, values(4), values(5), values(6), values(7),
      values(8))
  }
}
