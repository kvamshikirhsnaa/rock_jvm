package scala_new

object Test15 {
  def main(args: Array[String]): Unit = {

    val lst1 = List(2,4,8,6,12,13,11,10,3,7,5,1)
    val lst2 = List(16,15,8,6,12,13,11,10,3,7,5,14) // first ele large
    val lst3 = List(18,4,8,6,12,13,11,10,3,7,19,20) // last ele large

    def removeTwoEle(lst: List[_]): List[Int] = lst match {
      case lst: List[Int] => lst.filterNot{x =>
        (lst.indexOf(x) == (lst.indexOf(lst.max) + 1)) ||
          (lst.indexOf(x) == (lst.indexOf(lst.max) - 1))
      }
      case Nil => Nil
    }

    def secondMax(lst: List[Int]): Int = lst.filter{x =>
      x != lst.max}.max

    val lstnew1 = removeTwoEle(lst1)
    val lstnew2 = removeTwoEle(lst2)
    val lstnew3 = removeTwoEle(lst3)

    val max1 = lstnew1.max
    val max2 = lstnew2.max
    val max3 = lstnew3.max

    val maxnew1 = secondMax(lstnew1)
    val maxnew2 = secondMax(lstnew2)
    val maxnew3 = secondMax(lstnew3)

    println("Summing max numbers except 2 numbers besides max number")
    println("--------------------------------------------------------")
    println(s"lst1 is $lst1")
    println(s"lst2 is $lst2")
    println(s"lst3 is $lst3")
    println(s"lst1 max $max1 secondmax $maxnew1 , res ${max1 + maxnew1}")
    println(s"lst2 max $max2 secondmax $maxnew2 , res ${max2 + maxnew2}")
    println(s"lst3 max $max3 secondmax $maxnew3 , res ${max3 + maxnew3}")

  }

}
