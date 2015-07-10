/**
 * Created by rajan on 7/10/15.
 */
class Cell {

  private var mine: Boolean = false
  private var adjacentMine: Char = '*'

  def getVal: Char = adjacentMine

  def setVal(ch: Char) = {
    this.adjacentMine = ch
  }

  def setMine(b: Boolean) = {
    mine = b
  }

  def isMine: Boolean = mine
}