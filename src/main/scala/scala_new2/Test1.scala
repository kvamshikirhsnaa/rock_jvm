package scala_new2

import scala.collection.mutable.ListBuffer

object Test1 {
  def main(args: Array[String]): Unit = {

    val lst = (1 to 27).toList
    val batch = 6
    val lst2 = lst.sliding(batch, batch).toList

    val first = lst2.init
    val last = lst2.last


    println(lst)
    println(lst2)
    println(sample(first, last, List[List[Int]]()))
    println(sample2(first,last))

  }


  // using tail rec
  def sample(lst: List[List[Int]], xs: List[Int], lstnew: List[List[Int]]): List[List[Int]] = lst match {

    case head :: tail  if (xs.nonEmpty) => {
      val ls = List(lst.head :+ xs.head)
      sample(lst.tail, xs.tail,lstnew ::: ls)
    }
    case head :: _ => lstnew ::: List(head)
    case Nil => lstnew
  }

  // using map

  def sample2(lst: List[List[Int]], xs: List[Int]) = {
    var cnt = 0
    val batch = xs.size
    lst.map {
      case x if(cnt < batch) => {
        val res = x :+ xs(cnt)
        cnt += 1
        res
      }
      case y => y
    }
  }



}
