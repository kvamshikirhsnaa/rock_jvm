package scala_new

object Test19 {
  def main(args: Array[String]): Unit = {

    val lst = List(1,2,3,4,5,6,7,8,9)

    // Par: returns a parallel implementation of collection
    // on List returns ParVector
    // on Array returns ParArray

    val a = List(1,2,3,4,5,6)
    val b = Array(4,5,6,7,8,9)

    val parA = a.par
    val parB = b.par

    println(parA) // ParVector(1, 2, 3, 4, 5, 6)
    println(parB) // ParArray(4, 5, 6, 7, 8, 9)

    a.foreach(print) // 123456, returns same output how many times we run
    println()
    b.foreach(print)  // 456789, returns same output how many times we run
    println()

    // on List or Array when we applying computation no matter
    // what it will follow sequence only
    // the above one return same result how many times we print

    parA.foreach(print) // 541236 might be give diff output in next run
    println()
    parB.foreach(print) // 457869 might be give diff output in next run
    println()

    // on ParVector or ParArray when we applying computation
    // will follow parallel computation
    // the above one return different results for each run.

    // available parallel collections
    // ParSeq, ParVector, ParArray, ParMap, ParHashMap,
    // ParSet, ParHashSet, ParRange, ParIterable






  }

}
