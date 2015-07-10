import Array._

/**
 * Created by rajan on 7/10/15.
 */
class Board {
  var grid = ofDim[Cell](8, 8)

  for (i <- 0 until 8) {
    for (j <- 0 until 8)
      grid(i)(j) = new Cell()
  }
  placeAllMines()

  for (i <- 0 until 8) {
    for (j <- 0 until 8)
      print(grid(i)(j).getVal)
    println()
  }


  private def displayBoard(x: Int, y: Int): Unit = {
    var num = 0
    num = minesAround(x, y)

    if (num >= 0) grid(x)(y).setVal((num + 48).toChar)
    else clearBoard(x, y)

    for (i <- 0 until 8) {
      for (j <- 0 until 8)
        print(grid(i)(j).getVal)
      println("")
    }
  }

  private def lowerBound(x: Int) = if (x <= 0) 0 else x - 1

  private def upperBound(x: Int) = if (x >= 7) 7 else x + 1

  def minesAround(i: Int, j: Int): Int = {
    var num = 0;
    for (i <- lowerBound(i) until upperBound(i); j <- lowerBound(j) until upperBound(j))
      if (grid(i)(j).isMine) num = num + 1
    num
  }

  private def clearBoard(x: Int, y: Int): Unit = {
    var i = lowerBound(x)
    while (i <= upperBound(x)) {
      var j = lowerBound(y)
      while (j <= upperBound(y)) {
        if (minesAround(i, j) == 0) grid(i)(j).setVal('0')
        j = j + 1
      }
      i = i + 1
    }
  }

  private def placeMine(): Unit = {
    val x = (Math.random() * 7).toInt
    val y = (Math.random() * 7).toInt
    if (grid(x)(y).isMine equals false)
      grid(x)(y).setMine(true)
    else placeMine()
  }

  private def placeAllMines(): Unit = {
    for (i <- 0 until 8)
      placeMine()
  }

  def checkMines(x: Int, y: Int): Boolean = {

    if (grid(x)(y).isMine equals true) true
    else {
      var emptyCount = 0
      for (i <- 0 until 8; j <- 0 until 8) {
        if (grid(i)(j).getVal != '*') emptyCount = emptyCount + 1
        if (emptyCount == 54) println("You Win")
        else displayBoard(x, y)
      }
      false
    }
  }
}
