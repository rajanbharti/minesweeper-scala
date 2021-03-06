import scala.util.Random

class Board(rowsc: Int, columnsc: Int, minesc: Int, boardc: List[List[Cell]] = Nil) {
  val rows = rowsc
  val columns = columnsc
  val mines = minesc
  val board = if (boardc != Nil) boardc else initBoard()

  private def initBoard(): List[List[Cell]] = {
    val board1 = List.tabulate(rows)(_ => List.tabulate(columns)(_ => new Empty(false)))
    val board2 = initMines(mines, board1)
    initHints(board2, rows, columns)
  }

  private def initMines(quantity: Int, board: List[List[Cell]]): List[List[Cell]] = {
    val newboard = tryToPutMine(board)
    if (quantity > 1) {
      initMines(quantity - 1, newboard)
    } else {
      newboard
    }
  }

  private def putMine(x: Int, y: Int, board: List[List[Cell]]): List[List[Cell]] = {
    board.updated(x, board(x).updated(y, Mine(false)))
  }

  private def tryToPutMine(board: List[List[Cell]]): List[List[Cell]] = {
    val x = new Random().nextInt(rows)
    val y = new Random().nextInt(columns)
    val t: Cell = board(x)(y)
    t match {
      case Mine(_) => tryToPutMine(board)
      case _ => putMine(x, y, board)
    }
  }

  private def initHints(board: List[List[Cell]], x: Int, y: Int): List[List[Cell]] = {
    List.tabulate(x, y)((i, j) => transformIntoHint(board, i, j))
  }

  private def transformIntoHint(boardWithBombs: List[List[Cell]], x: Int, y: Int): Cell = {
    val cell = boardWithBombs(x)(y)
    cell match {
      case Mine(b) => Mine(b)
      case _ => initHint(boardWithBombs, x, y)
    }
  }

  private def initHint(boardWithBombs: List[List[Cell]], x: Int, y: Int): Cell = {
    def countBomb(cell: Cell): Int = {
      cell match {
        case Mine(_) => 1
        case _ => 0
      }
    }
    val neighborCells = getCellOnBoard(boardWithBombs, x - 1, y - 1) ::
      getCellOnBoard(boardWithBombs, x, y - 1) ::
      getCellOnBoard(boardWithBombs, x + 1, y - 1) ::
      getCellOnBoard(boardWithBombs, x - 1, y) ::
      getCellOnBoard(boardWithBombs, x + 1, y) ::
      getCellOnBoard(boardWithBombs, x - 1, y + 1) ::
      getCellOnBoard(boardWithBombs, x, y + 1) ::
      getCellOnBoard(boardWithBombs, x + 1, y + 1) :: Nil
    val hintValue = neighborCells.map(countBomb).sum
    hintValue match {
      case 0 => Empty(false)
      case _ => Hint(false, hintValue)
    }
  }

  def showCell(tuple: (Int, Int)): Board = {
    val x = tuple._1
    val y = tuple._2
    val cell = board(x)(y)
    cell match {
      case Empty(false) => showNeighborCells(x, y)
      case Mine(false) => {
        val newboard = board.updated(x, board(x).updated(y, Mine(true)))
        new Board(rows, columns, mines, newboard)
      }
      case Hint(false, hint) => {
        val newboard = board.updated(x, board(x).updated(y, Hint(true, hint)))
        new Board(rows, columns, mines, newboard)
      }
      case _ => new Board(rows, columns, mines, board)
    }
  }

  private def showNeighborCells(x: Int, y: Int): Board = {
    def getPosition(board: List[List[Cell]], x: Int, y: Int): List[(Int, Int)] = {
      if (contains(x, y)) {
        List(Tuple2(x, y))
      } else {
        Nil
      }
    }
    val newboard = board.updated(x, board(x).updated(y, Empty(true)))
    val newGame = new Board(rows, columns, mines, newboard)
    val neighborPositions: List[(Int, Int)] = getPosition(board, x - 1, y - 1) ++
      getPosition(board, x, y - 1) ++
      getPosition(board, x + 1, y - 1) ++
      getPosition(board, x - 1, y) ++
      getPosition(board, x + 1, y) ++
      getPosition(board, x - 1, y + 1) ++
      getPosition(board, x, y + 1) ++
      getPosition(board, x + 1, y + 1) ++ Nil
    neighborPositions.foldLeft(newGame)((game, tuple) => game.showCell(tuple))
  }

  def contains(x: Int, y: Int): Boolean = {
    x >= 0 && x < rows && y >= 0 && y < columns
  }

  def getCell(x: Int, y: Int): Cell = {
    if (contains(x, y)) {
      board(x)(y)
    } else {
      null
    }
  }

  def getCellOnBoard(board: List[List[Cell]], x: Int, y: Int): Cell = {
    if (contains(x, y)) {
      board(x)(y)
    } else {
      null
    }
  }
}


