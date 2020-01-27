package udemy_rockjvm

import scala.util.Random

object Sequences {
  def main(args: Array[String]): Unit = {


    // Range
    val aRange: Seq[Int] = 1 to 10
    aRange.foreach(x => print(x + " "))  // 1 2 3 4 5 6 7 8 9 10
    println()
    (1 to 5).foreach(x => print("hello" + " "))
    println()

    // Seq
    val aSeq = Seq(1,4,3,2)
    println(aSeq)   // List(1, 4, 3, 2)
    println(aSeq.reverse)   // List(2, 3, 4, 1)
    println(aSeq(2))   // 3
    println(aSeq.head)  // 1
    println(aSeq.tail)  // List(4, 3, 2)
    println(aSeq ++ Seq(6,5,8,7))   // List(1, 4, 3, 2, 6, 5, 8, 7)
    println(aSeq.sorted)    // List(1, 2, 3, 4)

    // List
/*
    list implementation
    sealed abstract class List[+A]
    case object Nil extends List[Nothing]
    case class ::[A](val hd: A, val tl: List[A]) extends List[A]

    A LinearSeq immutable linked list
    head, tail, isEmpty methods are fast: O(1)
    most operations are O(n): length, reverse
*/

    val aList = List(1,2,3)
    val prepended = 0 +: aList
    println(prepended)
    val appended = aList :+ 4
    println(appended)
    println( 0 +: aList :+ 4)

    // fill - is a curried function
    val apples5 = List.fill(5)("apple")
    println(apples5)

    // mkString
    println(apples5.mkString("_"))


    // Arrays
/*
    array implementation
    final class Array[T] extends java.io.Serializable with java.lang.Cloneable

    -> arrays are equivalent of simple java arrays
    -> can be manually constructed with predefined length
    -> can be mutated(updated in place)
    -> are interoperable with java's native arrays
    -> indexing is fast
*/
    val numbers = Array(1,2,3,4)
    val threeEles = Array.ofDim[Int](3)  // for Int default value is 0, returns Array(0,0,0)
    println(threeEles)
    threeEles.foreach(x => print(x + " "))
    println()
    val twoEles = Array.ofDim[String](2)  // for Int default value is null, returns Array(null,null)
    println(twoEles)
    twoEles.foreach(x => print(x + " "))
    println()

    // mutation
    numbers(2) = 0   // syntax sugar numbers.update(2, 0)
    println(numbers.mkString(" "))  // 1 2 0 4


    // Arrays and Seq
    // Array are equal to simple java arrays, but when Arrays can be converted to Seq
    // the result will be WrappedArray, this conversion will happen implicitly by scala

    val numbersSeq: Seq[Int] = numbers  // implicit conversion
    println(numbersSeq)  // WrappedArray(1, 2, 0, 4)


    // Vector
/*
     -> effectively constant indexed read and write: O(log32(n))
     -> fast element addition: append/prepend
     -> implemented as fixed branched trie (branch factor 32)
     -> good performance for large sizes
*/
    val aVector: Vector[Int] = Vector(1,2,3)
    println(aVector)
    println(aVector.updated(2, 0))
    println(aVector)

    // Vector vs List

    val maxRuns = 1000

    def getWriteTime(collection: Seq[Int]): Double = {
      val r = new Random
      val times = for {
        it <- 1 to maxRuns
      } yield {
        val currentTime = System.nanoTime()
        collection.updated(r.nextInt(collection.size), r.nextInt)
        System.nanoTime() - currentTime
      }

      times.sum * 1.0 / maxRuns
    }

    val numbersList = (1 to 1000000).toList
    val numbersVector = (1 to 1000000).toVector

    // advantage of list: keeps reference to tail
    // disadvantage of list: updating an element in middle of List takes long time
    println(getWriteTime(numbersList))   // 1.2078300737E7   very very very slow compare to Vector

    // advantage of Vector: depth of tree is small
    // disadvantage of Vector: needs to  replace entire 32 chunk
    println(getWriteTime(numbersVector))   // 4571.752










  }

}
