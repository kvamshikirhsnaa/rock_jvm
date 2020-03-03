package scala_new4

import scala.annotation.tailrec

object Test12 extends App {


  def justify(text: String, width: Int): String = {

    def createSpaces(n: Int) = (1 to n).map(_ => " ").mkString("")

    @tailrec
    def pack(words: List[String], currRow: List[String], currCharCnt: Int, res: List[List[String]]): List[List[String]] = {
      if (words.isEmpty && currRow.isEmpty) {
        res
      } else if (words.isEmpty) {
        res :+ currRow
      } else if (currRow.isEmpty && words.head.length > width) {
        val (partOnThisRow, partOnNextRow) = words.head.splitAt(width - 2)
        pack(partOnNextRow :: words.tail, List(), 0, res :+ List(partOnThisRow + "-"))
      } else if (currCharCnt + words.head.length > width) {
        pack( words, List(), 0, res :+ currRow )
      } else {
        pack(words.tail, currRow :+ words.head, currCharCnt + 1 + words.head.length, res)
      }
    }

    def justifyRow(row: List[String]): String = {
      if (row.length == 1) row.head
      else {
        val nSpacesAvailble = width - row.map(_.length).sum
        val nIntervals = row.length - 1
        val nSpacesPerInterval = nSpacesAvailble / nIntervals
        val nExtraSpacesPerInterval = nSpacesAvailble % nIntervals
        val regularSpace = createSpaces(nSpacesPerInterval)
        val biggerSpace =createSpaces(nExtraSpacesPerInterval + 1)

        if (nExtraSpacesPerInterval == 0) row.mkString(regularSpace)
        else {
          val nWordsOfBiggerSpace = nExtraSpacesPerInterval + 1
          val wordsOfBiggerSpace = row.take(nWordsOfBiggerSpace)
          val first = wordsOfBiggerSpace.mkString(biggerSpace)
          val second = row.drop(nWordsOfBiggerSpace).mkString(regularSpace)
          first + regularSpace + second
        }
      }
    }


    assert(width > 2)
    val words = text.split(" ").toList
    val unjustfiedRows = pack(words, List(), 0, List())
    val justifiedRows = unjustfiedRows.map(justifyRow)
    justifiedRows.mkString("\n")

  }


  def testJustfy() = {
    println( justify( "hi..! how are you? what are you doing? where are you? had your lunch?", 10 ) )
    println()
  }

  testJustfy

}
