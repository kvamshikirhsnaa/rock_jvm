package scala_new2

object Test2 {
  def main(args: Array[String]): Unit = {

    val lst = List(1,2,3,4,5,6,7,8,9)

    //view: returns a non-strict(lazy) version of collection

    val lst2 = lst.view
    val lst3 = lst2.map(x => x * 2)

    println(lst2) // SeqView(...)    lazy collections
    println(lst3) // SeqViewM(...)   lazy collections

    println(lst2.force) // force is used to get values on lazy collections
    println(lst3.force) // force is used to get values on lazy collections

    println((1 to 5).view)

    val evenList = (1 to 5).map(x => {Thread.sleep(1000); x * 2})
    // it sleeps for 1 sec for each iteration
    // total 5 ele so 5 sec will sleep and give results

    val lazyEvenList = (1 to 5).view.map(x => {Thread.sleep(1000); x * 2})
    // cuz of view, it will give result immediatly but it will compute
    // whenever required

    println(evenList)
    println(lazyEvenList)
    println(lazyEvenList.force) // now it will compute and sleep
    // for 1 sec for every iteration

    // Array is mutable, using Array, let's see View's Lazy Advantage


    val arr = (1 to 10).toArray

    val nonview = arr.slice(2,5)

    val view = arr.view.slice(2,5) // lazy collection

    nonview.foreach(x => print(x + " "))   // 3 4 5
    println()

    view.force.foreach(x => print(x + " ")) // 3 4 5
    println()

    // Updating Array

    arr(2) = 10

    nonview.foreach(x => print(x + " "))  // 3 4 5  cuz of not lazy value already computed so
    // it won't give updated value

    println()
    view.force.foreach(x => print(x + " "))  // 10 4 5 cuz of lazy, it will compute from arr again
    println()


    // changes on view also effect the mutable Array

    view(0) = 30
    view(1) = 50

    arr.foreach(x => print(x + " "))  // 1 2 30 50 5 6 7 8 9 10
    println()

    view.force.foreach(x => print(x + " ")) // 30 50 5







  }

}
