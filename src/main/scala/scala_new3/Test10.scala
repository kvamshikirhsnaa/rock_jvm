package scala_new3

import scala.collection.mutable

object Test10 {
  def main(args: Array[String]): Unit = {

    // Default List is immutable but
    // we have mutable "ListBuffer" and "MutableList"
    // ListBuffer and ArrayBuffer is most used for mutable collections
    // we can perform and convert to List after any updates deletes

    // MutableList

    val mlst = mutable.MutableList(4,5,6,7)

    println(mlst)  // MutableList(4, 5, 6, 7)

    // "+="  ->  it is for appending an element to MutableList
    // "+=:" ->  it is for prepending an element to MutableList
    // "++=" ->  it is for appending a collection(List or Seq or Array, Buffer) to MutableList
    // "++:" -> it is for appending and prepending a collection(List or Seq or Array, Buffer) to MutableList
               // but it creates new MutableList, it won't update to existing MutableList

    mlst += 8

    println(mlst)  // MutableList(4, 5, 6, 7, 8)

    3 +=: mlst

    println(mlst)  // MutableList(3, 4, 5, 6, 7, 8)

    mlst ++= List(9,10)

    println(mlst)  // MutableList(3, 4, 5, 6, 7, 8, 9, 10)

    println(List(1,2) ++: mlst)  // MutableList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    println(mlst)                // MutableList(3, 4, 5, 6, 7, 8, 9, 10)

    println(mlst ++: List(11,12))  // List(3, 4, 5, 6, 7, 8, 9, 10, 11, 12), returing List
                                   // to append a Seq use "++="

    println(mlst)  // MutableList(3, 4, 5, 6, 7, 8, 9, 10)

    println(List(1,2) ++: mlst ++: List(11,12))  // List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

    println(mlst)   // MutableList(3, 4, 5, 6, 7, 8, 9, 10)


  }

}
