package scala_new2

object Test9 {
  def main(args: Array[String]): Unit = {

    val lst = List(1,2,3,4,5,6,7,8,9)

    println(lst.sliding(2).toList)
    //List(List(1, 2), List(2, 3), List(3, 4), List(4, 5), List(5, 6), List(6, 7), List(7, 8), List(8, 9))
    println(lst.sliding(2,1).toList)
    //List(List(1, 2), List(2, 3), List(3, 4), List(4, 5), List(5, 6), List(6, 7), List(7, 8), List(8, 9))
    // above both are same

    println(lst.sliding(3).toList)
    //List(List(1, 2, 3), List(2, 3, 4), List(3, 4, 5), List(4, 5, 6), List(5, 6, 7), List(6, 7, 8), List(7, 8, 9))
    println(lst.sliding(3,2).toList)
    //List(List(1, 2, 3), List(3, 4, 5), List(5, 6, 7), List(7, 8, 9))
    println(lst.sliding(3,3).toList)
    //List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9))
    println(lst.sliding(3,4).toList)
    //List(List(1, 2, 3), List(5, 6, 7), List(9))
    println(lst.sliding(3,5).toList)
    //List(List(1, 2, 3), List(6, 7, 8))

    // grouped: it takes only one argument

    println(lst.grouped(2).toList) // List(List(1, 2), List(3, 4), List(5, 6), List(7, 8), List(9))
    println(lst.grouped(3).toList) // List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9))
    println(lst.grouped(4).toList) // List(List(1, 2, 3, 4), List(5, 6, 7, 8), List(9))
    println(lst.grouped(5).toList) // List(List(1, 2, 3, 4, 5), List(6, 7, 8, 9))
  }

}
