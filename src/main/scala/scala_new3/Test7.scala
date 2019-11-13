package scala_new3

import scala.collection.mutable.ArrayBuffer

object Test7 {
  def main(args: Array[String]): Unit = {

    // remove: ArrayBuffer has 2 types remove function.
    //  remove function takes index value, remove that element from ArrayBuffer, returns the removed element
    // another remove takes index value and count(after that index value how many numbers want to remove)
    // it will remove elements in ArrayBuffer starting from index value to given count
    // this remove method returns Unit, it just remove elements in ArrayBuffer, it won't return removed elements


    val arr = ArrayBuffer.range('a', 'i')
    println(arr) // ArrayBuffer(a, b, c, d, e, f, g, h)

    println(arr.remove(0)) // a

    println(arr) // ArrayBuffer(b, c, d, e, f, g, h)

    println(arr.remove(2,3)) // () returns nothing

    println(arr) // ArrayBuffer(b, c, g, h), here 2 index value is d, from d , 3 elements are removed

    val arr2 = ArrayBuffer.range('a', 'i')

    println(arr2) // ArrayBuffer(a, b, c, d, e, f, g, h)

    arr2.trimStart(2) // it will remove elements upto given number index from starting
                      // in ArrayBuffer, returns nothing

    println(arr2) // ArrayBuffer(c, d, e, f, g, h)

    arr2.trimEnd(3) // it will remove elements from given number index to ending
                             // in ArrayBuffer, returns nothing

    println(arr2) // ArrayBuffer(c, d, e)







  }

}
