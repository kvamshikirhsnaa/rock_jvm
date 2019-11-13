package scala_new2

object Test10 {
  def main(args: Array[String]): Unit = {

    val lst = List(1,2,3,4,5,6,7,8,9)

    //contains: gives boolean based on predicate
    // contains avail in collections and Strings
    // can't apply on Numeric types, Int, Double,etc
    println(lst.contains(4)) // true
    println(lst.contains(10)) // flase

    //println(lst.exists(x => x.contains(10))) //
    // contains not available on Int

    // exists: it will give true if any element in collection(list)
    // satisfies the predicate

    val lst2 = List("saikrishna", "aravind", "prakash")

    println(lst2.exists(x => x.contains("i"))) // true


    // forall: it will give true if all elements in collection(list)
    //   satisfies the predicate

    println(lst2.forall(x => x.contains("i"))) // false

    println(lst2.filter(x => x.contains("i"))) //List(saikrishna, aravind)

  }

}
