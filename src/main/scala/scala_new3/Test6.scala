package scala_new3

import scala.collection.mutable.ArrayBuffer

object Test6 {
  def main(args: Array[String]): Unit = {

    // Stack: It works on the concept of LIFO. it has push and pop method. you have
    // both mutable and immutable version of Stack

    val numStack = scala.collection.mutable.Stack(1,2,3)

    println(numStack) // Stack(1, 2, 3)

    numStack.push(4)

    println(numStack) // Stack(4, 1, 2, 3)

    numStack.push(5)
    numStack.push(6,7,8)

    println(numStack) // Stack(8, 7, 6, 5, 4, 1, 2, 3)
    println(numStack.top) // 8

    println(numStack.pop()) // 8, it will give last inserted value inserted value in Stack
                             // also that element will be removed from Stack

    println(numStack) // Stack(7, 6, 5, 4, 1, 2, 3)


    // ArrayStack: It provides fast indexing and performs better than mutable stack


    val arrStack = scala.collection.mutable.ArrayStack(1,2,3)
    println(arrStack)       // ArrayStack(1, 2, 3)
    arrStack.push(4)
    arrStack.push(5)
    arrStack.push(6)
    println(arrStack)      // ArrayStack(6, 5, 4, 1, 2, 3)
    println(arrStack.pop())   // 6
    println(arrStack)      // ArrayStack(5, 4, 1, 2, 3)
    println(arrStack.top)    // 5
    println(arrStack.indexOf(2))  // 3


    // ArrayBuffer: Array doesn't grow(fixed in size), so ArrayBuffer can increase the size

    val arr = ArrayBuffer(1,2,3)
    println(arr)  // ArrayBuffer(1, 2, 3)
    arr.append(4)
    arr.append(5,6)
    println(arr)  // ArrayBuffer(1, 2, 3, 4, 5, 6)
    arr.appendAll(Seq(7,8))
    println(arr)  // ArrayBuffer(1, 2, 3, 4, 5, 6, 7, 8)
    arr.clear()
    println(arr)  // ArrayBuffer()


    val arr2 = ArrayBuffer(9,10)
    println(arr2) // ArrayBuffer(9, 10)
    arr2.insert(0,6) // 0 is index value, add 6 at index 0
    println(arr2)  // ArrayBuffer(6, 9, 10)
    arr2.insert(1,7,8)  // 1 is index value, add 7,8 at index 1
    println(arr2)  // ArrayBuffer(6, 7, 8, 9, 10)
    arr2.insertAll(0,Vector(4,5))
    println(arr2)  // ArrayBuffer(4, 5, 6, 7, 8, 9, 10)
    arr2.prepend(3)
    println(arr2)  // ArrayBuffer(3, 4, 5, 6, 7, 8, 9, 10)
    arr2.prependAll(Seq(0,1,2))
    println(arr2)  // ArrayBuffer(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    // append: will add the element at last of ArrayBuffer
    // prepend: will add the element at first of ArrayBuffer
    // insert: we can insert element(elements, using Seq or Vector) where ever in ArrayBuffer using index value.


  }

}
