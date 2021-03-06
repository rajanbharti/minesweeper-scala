abstract class Cell(val shown: Boolean)

case class Mine(override val shown: Boolean) extends Cell(shown)

case class Empty(override val shown: Boolean) extends Cell(shown)

case class Hint(override val shown: Boolean, val mineCount: Int) extends Cell(shown) {
}