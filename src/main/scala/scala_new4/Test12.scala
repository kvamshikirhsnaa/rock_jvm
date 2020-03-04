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
        val biggerSpace = createSpaces(nSpacesPerInterval + 1)

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
    println(justify( "hi..! how are you? what are you doing? where are you? had your lunch?", 10))
    println()
    println(justify("Had an extensive review regarding preparedness on the COVID-19 Novel Coronavirus. Different ministries & states are working together, from screening people arriving in India to providing prompt medical attention, said PM Modi. Earlier, PM Modi also met Delhi CM Arvind Kejriwal, in which the two leaders discussed the coronavirus spread. Arvind Kejriwal, on the other hand, has called a meeting with Health Minister Satyendar Jain and officials to take stock of the government's preparations to deal with coronavirus. Congress leader Rahul Gandhi hit out at the government and said a true leader would be completely focused on averting the massive crisis about to be unleashed by the virus on the country and its economy.", 40))
  }

  testJustfy

}
