package scala_new4

object Test13 extends App {

  // products of array except it self

  // time complexity: O(n)
  def products(lst: List[Int]): List[Int] = {

    if (lst.contains(0)) List.empty

    else {

      val left = lst.map { x =>
        val lstnew = lst.take( lst.indexOf( x ) )
        lstnew.product
      }

      val right = lst.map { x =>
        val lstnew = lst.drop( lst.indexOf( x ) + 1 )
        lstnew.product
      }
      left.zip( right ).map( x => x._1 * x._2 )
    }
  }

  val a = List(1,2,3,4)
  // the array product is 24, we have to get 24 products except input elements

  println(products(a)) // List(24, 12, 8, 6)

  val b = List(2,3,1,4,5) // 120

  println(products(b))  // List(60, 40, 120, 30, 24)

  val c = List(4,5,1,8,2) // 320

  println(products(c))  // List(80, 64, 320, 40, 160)

  val d = List(1,4,5,3,0)

  println(products(d)) //  List()

}
