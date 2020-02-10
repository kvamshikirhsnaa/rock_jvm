package scala_new4

import scala.annotation.tailrec

object Test8 {
  def main(args: Array[String]): Unit = {

    val lst = List(2,1,3,5,3,2)
    val lst2 = List(2,1,3,1,5,3,2,5)
    val lst3 = List(2,3,1,4,6,5)
    println(firstDuplicate(lst))
    println(firstDuplicate(lst2))
    println(firstDuplicate(lst3))

  }


  def firstDuplicate(lst: List[Int]): Int = {
    @tailrec
    def firstDupTailrec(ind: Int, acc: List[Int]): Int = {
      if (ind >= lst.length) throw new RuntimeException("No Duplicate Element")
      else if (acc.contains(lst(ind))) lst(ind)
      else firstDupTailrec(ind + 1, lst(ind) +: acc)
    }
    firstDupTailrec(0, List.empty)
  }

}
