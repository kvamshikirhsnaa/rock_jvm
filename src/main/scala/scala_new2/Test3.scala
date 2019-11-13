package scala_new2

object Test3 {
  def main(args: Array[String]): Unit = {

    val lstnew = List(1,2,3,4,5)

    // updated: List is immutable updated function gives new list with updated values

    val lstnew2 = lstnew.updated(0,10)     // gives new List old list will have same result

    println(lstnew2) // List(10, 2, 3, 4, 5)

    // update: Array is mutable, update function will change the existed Array

    val arr = Array(1,2,3,4,5)

    arr.foreach(x => print(x + " ")) // 1 2 3 4 5
    println()

    // after updating arr
    arr.update(0,10) // (or) arr(0) = 10  both are same
    // on List we cann't do like this, cuz
    // it is immutable

    arr.foreach(x => print(x + " ")) // 10 2 3 4 5
    println()

    // for Arrays, updated, also function available, it will
    // give new updated Array, old Array will contain same old elements

    arr.updated(0, 25).foreach(x => print(x + " ")) // 25 2 3 4 5
    println()

    arr.foreach(x => print(x + " "))  // 10 2 3 4 5





  }

}
