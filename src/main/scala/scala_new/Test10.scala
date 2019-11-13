package scala_new

import scala.annotation.tailrec

object Test10 {
  def main(args: Array[String]): Unit = {


    //flatten: converts a collection of collection(such as list of lists) to
    // a single collection(single list). it is usually used to eliminate
    // undesired collection nesting

    val numlst1 = List(1,2,3,4,5)
    val numlst2 = List(6,7,8,9,10)

    val lst = List(numlst1, numlst2)

    println(lst.flatten)

    val lst1 = List(List(1,2,3,4,List(5,6,7, List(8,9,10))), List(11,12,13))

    val lst2 = List(1,2,3,4,5,6,7,8,9)

    val lst3 = List(List(1,2,(3,4),List(5,6,7, List(8,9,10))), List(11,12,13))

    val lst4 = List(List(1,2,(3,4,100),List(5,6,7, List(8,9,10))), List(11,12,13))

    println(lst1.flatten)
    println(lst1.flatMap(x => x))

    // flattening all nested lists with in list of lists

    // using recursion
    def flatten(lst: List[Any]): List[Any] = lst.flatMap {
        case ls: List[Any] => flatten( ls )
        case e => List( e )
    }


    println(flatten(lst1))



    // using tail recursion
    def flattenTailRec(ls: List[Any]): List[Any] = {
      @tailrec
      def flattenR(res: List[Any], rem: List[Any]): List[Any] = rem match {
        case Nil                     => res.reverse
        case (head: List[_]) :: tail => flattenR(res, head ::: tail)
        case (head: Product) :: tail => {
          val lst = head.productIterator.toList
          flattenR(res, lst ::: tail)
        }
        case element :: tail         => flattenR(element +: res, tail)
      }
      flattenR(List(), ls)
    }

    println("flattenTailRec")
    println("--------------")
    println(flattenTailRec(lst1))
    println(flattenTailRec(lst2))
    println(flattenTailRec(lst3))
    println(flattenTailRec(lst4))

    // flatMap works on Some and None also
    // if list contains elements of Some and None then if we use flatten or flatMap it filters
    // out the None values
    // flatMap => map + flatten


    val lstnew = (1 to 10).toList.map(x => if (x % 2 == 0) Some(x) else None)

    println(lstnew)

    println(lstnew.flatten)
    println((1 to 10).toList.flatMap(x => if (x % 2 == 0) Some(x) else None))


    val pairs = Map("k1" -> 1, "k2" -> 2, "k3" -> 3)

    println(pairs.map(x => (x._1 , x._2 * 2)))

    // if don't want to use ._1, ._2,  we can use case

    println(pairs.map {case (k, v) => (k, v * 2)})

    // Map returning List

    println(pairs.map {case (k, v) => v * 2})

    // Map returning Set

    println(pairs.map {case (k, v) => v * 2}.toSet)






  }

}
