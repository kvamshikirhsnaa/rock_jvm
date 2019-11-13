package scala_new

object Test18 {
  def main(args: Array[String]): Unit = {

    val lst1 = List(1,2,3,4,5,6)
    val lst2 = List(6,5,4,7,8,9)

    // union, ++, ::: these 3 will combine 2 collections
    // ::: mostly used to combines collections

    val unires = lst1.union(lst2)

    println(unires) // List(1, 2, 3, 4, 5, 6, 6, 5, 4, 7, 8, 9)
    println(lst1 ++ lst2) // List(1, 2, 3, 4, 5, 6, 6, 5, 4, 7, 8, 9)
    println(lst1 ::: lst2) // List(1, 2, 3, 4, 5, 6, 6, 5, 4, 7, 8, 9)


    // SET: toSet remove duplicates returns Set, doesn't follow order
    // distinct also remove duplicates returns Lists, follows same order

    println(unires.distinct) // List(1, 2, 3, 4, 5, 6, 7, 8, 9)
    println(unires.toSet)  // Set(5, 1, 6, 9, 2, 7, 3, 8, 4)

    //count: count takes predicate
    //     return no.of elements satisfies predicate
    println(unires.count(x => x > 5)) // 5

    // intersect: returns common elements from both collections

    println(lst1.intersect(lst2)) // List(4, 5, 6)
    println(lst2.intersect(lst1))  // List(6, 5, 4)


    // diff: returns values from first collection not in second collection

    println(lst1.diff(lst2)) // List(1, 2, 3)
    println(lst2.diff(lst1))  // List(7, 8, 9)

    // isEmpty: returns true if collection is empty else false

    println(List().isEmpty) // true
    println(lst1.isEmpty)  // false

    // nonEmpty: returns true if collection is not empty else false

    println(List().nonEmpty) // false
    println(lst1.nonEmpty)  // true


    // max: returns max element in list,
    // if list is String type, it checks each chars value

    val lstnew = List("aaaa", "bbb", "cc", "d")
    println(lstnew.max) // d

    val emplst = List() // type is Nothing
    val emplst1 = List[Int]() // type is Int, still list is empty
    // println(emplst.max) // UnsupportedOperationException
    // println(emplst1.max)  // UnsupportedOperationException

    // maxBy: can check max using predicate

    println(lstnew.maxBy(x => x.size)) // aaaa

    // same like max we can do min and minBy








  }

}
