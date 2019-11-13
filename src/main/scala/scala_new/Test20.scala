package scala_new

import scala.collection.immutable

object Test20 {
  def main(args: Array[String]): Unit = {

    val lst = List(1,2,3,4,5)

    //sum: adds all elements in collection,
    //     collections should be numeric type
    println(lst.sum)

    //produce: multiply all elements in collection,
    //     collections should be numeric type

    println(lst.product) // 5 factorial => 120

    val lst2 = List(10,20,30,40,50,60,70,80,90)

    // slice(inc, exc)
    println(lst2.slice(2,6)) // List(30, 40, 50, 60)

    //splitAt(int): takes index, and splits the collection
    //            based on given index value
    // return 2 separate collections(Lists) in Tuple

    println(lst2.splitAt(5)) // (List(10, 20, 30, 40, 50),List(60, 70, 80, 90))

    // splitAt is available in numeric collection type, split and splitAt available on String.

    // sorted:

    val lst3 = List(2,5,6,7,8,3)
    val lst4 = List("saikrishna", "vamshikrishna", "aravind", "prakash", "narahari")

    println(lst3.sorted) // List(2, 3, 5, 6, 7, 8)
    println(lst4.sorted) // List(aravind, narahari, prakash, saikrishna, vamshikrishna)

    // sortBy: can be used to do custom sorting based on condition
    // default sortBy is ascending, for desc we need to use "-" before the condition

    println(lst4.sortBy(x => x.size)) // List(aravind, prakash, narahari, saikrishna, vamshikrishna), asc
    println(lst4.sortBy(x => -x.size)) // List(vamshikrishna, saikrishna, narahari, aravind, prakash), desc
    println(lst4.sortBy(x => x.charAt(1))) // List(saikrishna, vamshikrishna, narahari, aravind, prakash), asc


    // sortWith: can be used to do custom sorting based on condition,
    // it take 2 args and check the predicate
    println(lst4.sortWith((x,y) => x.size < y.size)) // List(aravind, prakash, narahari, saikrishna, vamshikrishna)
    println(lst4.sortWith((x,y) => x.size > x.size)) // List(saikrishna, vamshikrishna, aravind, prakash, narahari)

    println(lst3.sortWith((x,y) => x < y)) // List(2, 3, 5, 6, 7, 8)
    println(lst3.sortWith((x,y) => x > y)) // List(8, 7, 6, 5, 3, 2)


    // sortBy:
    val empSal = Map("saikrishna" -> 100000, "aravind" -> 80000, "prakash" -> 40000,
      "narahari" -> 60000, "anji" -> 50000)

    // sortBy not available on Map so we convert it into ListMap

    println(empSal.toSeq) // ArrayBuffer((prakash,40000), (saikrishna,100000), (aravind,80000),
    // (anji,50000), (narahari,60000))

    println(empSal.toSeq.sortBy(x => x._2))  // ArrayBuffer((prakash,40000), (anji,50000),
    // (narahari,60000), (aravind,80000), (saikrishna,100000)), sorted

    // inside ArrayBuffer every element is Map, key value pair

    val lstmap = immutable.ListMap(empSal.toSeq.sortBy(x => x._2):_*)

    // when we do :_* every element extracted as Map give below result
    // Map doesn't preserve the order, that is why we are using ListMap, it follows order

    println(lstmap) // Map(prakash -> 40000, anji -> 50000, narahari -> 60000, aravind -> 80000, saikrishna -> 100000)

    // above output is Map type only


    val lstmap2 = immutable.List(empSal.toSeq.sortBy(x => x._2):_*)
    println(lstmap2)

    // converting Map to List

    val lstmap3 = empSal.toList

    println(lstmap3.sortBy(x => x._2)) // List((prakash,40000), (anji,50000), (narahari,60000), (aravind,80000), (saikrishna,100000))

    // above output is List of Maps






  }

}
