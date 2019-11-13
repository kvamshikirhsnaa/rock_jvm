package scala_new

object Test9 {
  def main(args: Array[String]): Unit = {

    val lst = List(2,4,6,8,9,10,12,13,15,16)

    println(lst.takeWhile(x => x % 2 == 0))
    println(lst.dropWhile(x => x % 2 == 0))

    println(lst.takeWhile(x => x % 2 == 1))
    println(lst.dropWhile(x => x % 2 == 1))

    //span: is combination of takeWhile and dropWhile
    // result will be in tuple of Lists.

    println(lst.span(x => x % 2 == 0))
    println(lst.span(x => x % 2 == 1))


    //partition: return 2 separate Lists in tuple based on predicate

    println(lst.partition(x => x % 2 == 0))

    //exists: returns true if the predicate is true for any element in the List

    println(lst.exists(x => x % 2 == 0))  // true
    println(lst.exists(x => x > 50))  // false

    // find: returns the first element that matches the predicate as Some[A],
    // returns None if no match found in List

    println(lst.find(x => x % 2 == 0))
    println(lst.find(x => x % 2 == 1))
    println(lst.find(x => x == 50))

    // filter: if predicate is true returns those values

    println(lst.filter(x => x % 2 == 0)) // print even values

    // filterNot: if predicate is true do not return those values

    println(lst.filterNot(x => x % 2 == 0)) // don't print even values

    // forall: it returns true if predicate is true for all elements in list

    println(lst.forall(x => x < 50)) // true
    println(lst.forall(x => x % 2 == 0)) // false

    // lift: lift will take int, given integer index value will be
    // returned as Option, if the collection doesn't has index of given
    // number returns None

    val lstnew = List(1,2,7,4,5,6)

    println("LIFT:")
    println("-----")
    println(lstnew.lift(1)) // Some(2)
    println(lstnew.lift(5)) // Some(6)
    println(lstnew.lift(10)) // None

    // indexOf: returns index of given integer, if integer is not
    // in collection returns -1

    println("INDEXOF:")
    println("--------")
    println(lstnew.indexOf(1)) // 0
    println(lstnew.indexOf(6))  // 5
    println(lstnew.indexOf(3))  // returns -1 cuz 3 is not in List
                               // won't throw arrayIndexOutOfBoundException

  }

}
