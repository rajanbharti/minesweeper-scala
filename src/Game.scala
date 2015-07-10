/**
 * Created by rajan on 7/10/15.
 */
class Game {
  var b: Board = new Board()

  def start(): Unit = {
    println("Enter x")
    val x = Console.readInt()
    println("Enter y")
    val y = Console.readInt()
    if (b.checkMines(x, y) equals true) println("Game Over !!")
    else start()
  }
}
