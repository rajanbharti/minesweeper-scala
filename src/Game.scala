/**
 * Created by rajan on 7/14/15.
 */
class Game {
  val board: Board = new Board(8, 8, 10)
  var tempBoard: Board = board

  def start(): Unit = {
    println("Enter x")
    val x = Console.readInt()
    println("Enter y")
    val y = Console.readInt()
    if (x >= 0 && x < 8 && y >= 0 && y < 8) {
      val tempCell: Cell = tempBoard.getCell(x, y)
      tempCell match {
        case Mine(_) => {
          tempBoard = tempBoard.showCell(x, y)
          displayBoard(tempBoard)
          println("Game Over!")
        }
        case Empty(_) => {
          tempBoard = tempBoard.showCell(x, y)
          displayBoard(tempBoard)
          start()
        }
        case Hint(_, _) => {
          tempBoard = tempBoard.showCell(x, y)
          displayBoard(tempBoard)
          start()
        }
      }
    }
    else {
      println("Enter valid cell")
      start()
    }

  }

  private def displayBoard(tempBoard: Board): Unit = {
    for (i <- 0 until 8) {
      for (j <- 0 until 8) {
        tempBoard.board(i)(j) match {
          case Mine(true) => print("*")
          case Empty(true) => print(" ")
          case Hint(true, _) => print _
          case _ => print("X")
        }
      }
      println()
    }
  }
}
