package scala_new3

import scala.collection.mutable.ArrayBuffer

object Test12 {
  def main(args: Array[String]): Unit = {

    // ArrayBuffer: it is mutable, very good performance for
    // prepending, appending and removing an element or collection
    // very good for random updating and searching(accessing) randomly based on index


    // "+="   -> it is for appending an element to the ArrayBuffer
              // tuple is not collection like List, Array, Set, Map, Vector
              // using "+=" also can append a tuple to ListBuffer
              // but can't prepend tuple to ListBuffer
    // "+=:"  -> it is for prepending an element to the ArrayBuffer
    // "++="  -> it is for appending a collection to the ArrayBuffer
    // "++=:" -> it is for prepending a collection to the ArrayBuffer

    // "-="   -> it is for removing an element from the ArrayBuffer
               // if element is not avail in ArrayBuffer it won't throw any exception
               // ArrayBuffer doesn't effect any changes
    // "--="  -> it is for removing a collection from the ArrayBuffer
              // if elements in collection or whole collection is not avail in ArrayBuffer
              // it won't throw any exception ArrayBuffer doesn't effect any changes



    val abuf = ArrayBuffer(4,5,6)

    println(abuf)       // ArrayBuffer(4, 5, 6)

    abuf += 7

    println(abuf)      // ArrayBuffer(4, 5, 6, 7)

    3 +=: abuf

    println(abuf)      // ArrayBuffer(3, 4, 5, 6, 7)

    abuf ++= List(8,9,10)

    println(abuf)      // ArrayBuffer(3, 4, 5, 6, 7, 8, 9, 10)

    List(1,2) ++=: abuf

    println(abuf)      // ArrayBuffer(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    abuf -= 5

    println(abuf)      // ArrayBuffer(1, 2, 3, 4, 6, 7, 8, 9, 10)

    abuf --= List(3,7,9)

    println(abuf)      // ArrayBuffer(1, 2, 4, 6, 8, 10)

    abuf(2) = 3

    abuf(5) = 9

    println(abuf)      // ArrayBuffer(1, 2, 3, 6, 8, 9)

    abuf

    abuf += (10,11,12)

    println(abuf)   //  ArrayBuffer(1, 2, 3, 6, 8, 9, 10, 11, 12)


    // "remove"  -> 2 types of remove functions available,
    // (i) remove will take Int(index), remove that indexed element from ArrayBuffer
    //        if given index does't exist throws IOBE, returns removed element
    // (ii) remove will take 2 Int arguments, one is index value, next argument is count
    //        from given index it will remove all elements up to given count.
    //        it throws IOBE,not like ListBuffer, if index value avail and count value is more
    //        than ArrayBuffer, returns nothing(unit)

    println(abuf.remove(5))  // 9

    println(abuf)           // ArrayBuffer(1, 2, 3, 6, 8, 10, 11, 12)

    // println(abuf.remove(10))  // throws IOBE, as 10 index is not avail in ArrayBuffer

    abuf.remove(2,3) // returns nothing, just remove 3 elements from index 2 in ArrayBuffer

    println(abuf)   // ArrayBuffer(1, 2, 10, 11, 12)

    //abuf.remove(3,10)   // here ArrayBuffer doesn't has 10 elements from index 3 so it throws IOBE

    abuf -= 50   // 50 is not avail in ArrayBuffer so no changes to ArrayBuffer, no exception throwing

    println(abuf)   // ArrayBuffer(1, 2, 10, 11, 12)

    abuf --= List(2,11,15,16) // only 2 and 11 will be removed

    println(abuf)   // ArrayBuffer(1, 10, 12)

    abuf --= List(50,100)  // nothing is avail so no changes to ArrayBuffer

    println(abuf)   // ArrayBuffer(1, 10, 12)














  }

}
