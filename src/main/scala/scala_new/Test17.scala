package scala_new

object Test17 {
  def main(args: Array[String]): Unit = {


    val ls = List()
    val lst = List(1,2,3,4,5,6,7,8,9)

    // head: on empty list throws error
    //println(ls.head) // error NoSuchElementException
    println("HEAD & HEADOPTION")
    println("------------------")
    println(ls.headOption) // None
    println(lst.head)  // 1
    println(lst.headOption)  // Some(1)

    // tail: returns list except head,
     //on empty collection throws UnsupportedOperationException
    // tails: returns non-empty-iterator even for empty list
    println("TAIL & TAILS")
    println("-------------")
    println(lst.tail)  // List(2, 3, 4, 5, 6, 7, 8, 9)
    //println(ls.tail) // UnsupportedOperationException
    println(lst.tails) // non-empty- iterator
    println(ls.tails) // non-empty- iterator
    ls.tails.foreach(println) // returns empty List

    // init: returns collection(list) except last element in collection
       //on empty collection throws UnsupportedOperationException
    // inits: returns non-empty-iterator except last element in it
    println("INIT & INITS")
    println("-------------")
    println(lst.init) // List(1, 2, 3, 4, 5, 6, 7, 8)
   // println(ls.init)  // UnsupportedOperationException
    println(ls.inits)  // non-empty-iterator
    println(lst.inits)  // non-empty-iterator


    // last: on empty list throws error
    //println(ls.last) // error NoSuchElementException
    println("LAST & LASTOPTION")
    println("------------------")
    println(ls.lastOption) // None
    println(lst.last)  // 9
    println(lst.lastOption)  // Some(9)


    // reduceLeftOption can be used to handle empty collections

    val a = List[Int]()

    //println(a.reduceLeft(_ + _)) // throws UnsupportedOperationException
    println(a.reduceLeftOption(_ + _))

    // reduceLeftOption, reduceRightOption, reduceOption all
    // are used to handle empty collections

  }

}
