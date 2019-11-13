package scala_new

object Test13 {
  def main(args: Array[String]): Unit = {

    val lst = List(1,2,3,4,5,6,7,8,9,10)

    // sliding:
    println("SLIDING:")
    println("--------")
    val res1 = lst.sliding(2).toList
    println(res1)

    println(lst.sliding(3).toList)

    //sliding and windowing
    val res2 = lst.sliding(3,2).toList
    println(res2)


    val res3 = lst.sliding(2,3).toList
    println(res3)

    val res4 = lst.sliding(2,4).toList
    println(res4)

    println((0 to 6).toList) // includes 6 also

    val lstnew = List(1,5,2,7,9,1,5,9,4,9,4,9)

    println("Return all max elements in list")
    println("--------------------------------")

    val lstnew2 = lstnew.filterNot(x => x != lstnew.max)

    println(lstnew)
    println(lstnew2)



  }

}
