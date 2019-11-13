package scala_new3

import scala.collection.mutable.ListBuffer

object Test11 {
  def main(args: Array[String]): Unit = {

    // ListBuffer: it is mutable, very good performance for
    // prepending, appending and removing an element or collection
    // not great for random searching and updating

    // "+="   -> it is for appending an element to the ListBuffer
              // tuple is not collection like List, Array, Set, Map, Vector
              // using "+=" also can append a tuple to ListBuffer
              // but can't prepend tuple to ListBuffer
    // "+=:"  -> it is for prepending an element to the ListBuffer
    // "++="  -> it is for appending a collection to the ListBuffer
    // "++=:" -> it is for prepending a collection to the ListBuffer

    // "-="   -> it is for removing an element from the ListBuffer
              // if element is not avail in ListBuffer it won't throw any exception
              // ListBuffer doesn't effect any changes
    // "--="  -> it is for removing a collection from the ListBuffer
              // if elements in collection or whole collection is not avail in ListBuffer
              // it won't throw any exception ListBuffer doesn't effect any changes


    val lbuf = ListBuffer(4,5,6,7)

    println(lbuf)  // ListBuffer(4, 5, 6, 7)

    lbuf += 8

    println(lbuf)  // ListBuffer(4, 5, 6, 7, 8)

    3 +=: lbuf

    println(lbuf)    // ListBuffer(3, 4, 5, 6, 7, 8)

    lbuf ++= List(9,10)

    println(lbuf)    // ListBuffer(3, 4, 5, 6, 7, 8, 9, 10)

    List(1,2) ++=: lbuf

    println(lbuf)    // ListBuffer(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    lbuf -= 8

    println(lbuf)    // ListBuffer(1, 2, 3, 4, 5, 6, 7, 9, 10)

    lbuf --= List(1,2,10)

    println(lbuf)    // ListBuffer(3, 4, 5, 6, 7, 9)

    lbuf(5) = 8

    println(lbuf)    // ListBuffer(3, 4, 5, 6, 7, 8), but for updates ArrayBuffer is good compare to ListBuffer

    lbuf += (9,10,11)

    println(lbuf)   //  ListBuffer(3, 4, 5, 6, 7, 8, 9, 10, 11)


    // "remove"  -> 2 types of remove functions available,
    // (i) remove will take Int(index), remove that indexed element from ListBuffer
    //        if given index does't exist throws IOBE, returns removed element
    // (ii) remove will take 2 Int arguments, one is index value, next argument is count
    //        from given index it will remove all elements up to given count it won't throw IOBE,
    //        if index value avail and count value is more than ListBuffer, returns nothing(unit)

    println(lbuf.remove(5))  // 8

    println(lbuf)   // ListBuffer(3, 4, 5, 6, 7, 9, 10, 11)

    // println(lbuf.remove(8))  // throws IOBE, as 8 index is not avail in ListBuffer

    lbuf.remove(2,3) // returns nothing, just remove 3 elements from index 2 in ListBuffer

    println(lbuf)   // ListBuffer(3, 4, 9, 10, 11)

    lbuf.remove(3,10)   // here ListBuffer doesn't has 10 elements from index 3 but it won't throw any exception
                        // remove all elements to end of ListBuffer but ArrayBuffer throws IOBE

    println(lbuf)    // ListBuffer(3, 4, 9)

    lbuf.remove(5,3)  // here ListBuffer doesn't has index 5 still it won't throw exception, just removes nothing
                      // but ArrayBuffer throws IOBE

    println(lbuf)     // ListBuffer(3, 4, 9)

    lbuf -= 50        // 50 is not avail in ListBuffer, so no changes

    println(lbuf)     // ListBuffer(3, 4, 9)

    lbuf --= List(4,10,20)  // only 4 is available in ListBuffer, it just removes 4

    println(lbuf)         // ListBuffer(3, 9)

    lbuf --= List(50,100)  // nothing is avail in ListBuffer, so no changes

    println(lbuf)         // ListBuffer(3, 9)












  }

}
