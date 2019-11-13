package scala_new3

object Test9 {
  def main(args: Array[String]): Unit = {

    // Vector: it is immutable IndexedSeq collection

    var eid = Vector(10)

    println(eid)      // Vector(10)
    println(eid(0))   // 10

    // eid(0) = 20 // error: update is not a member of scala.collection.mutable.Vector

    println(eid :+ 11)   // Vector(10, 11)
    println(eid)         // Vector(10), Vector is immutable, above one creates new Vector


    // range:

    Array.range(1,10).foreach(x => print(x + " "))  // 1 2 3 4 5 6 7 8 9
    println()

    println(List.range(1,10))                     // List(1, 2, 3, 4, 5, 6, 7, 8, 9)

    println(Vector.range(1,10,3))                // Vector(1, 4, 7)


    println((0 to 10).toList)          // List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println((0 to 10).toVector)        // Vector(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    (0 to 10).toArray.foreach(x => print(x + " "))    // 0 1 2 3 4 5 6 7 8 9 10
    println()
    println((0 to 10).toSet)           // Set(0, 5, 10, 1, 6, 9, 2, 7, 3, 8, 4)
    // Set doesn't follow order




  }

}
