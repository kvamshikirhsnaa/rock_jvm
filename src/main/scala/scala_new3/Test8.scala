package scala_new3

object Test8 {
  def main(args: Array[String]): Unit = {

    // Queue: it is based on FIFO,
    // scala has both mutable and immutable Queue

    val q = scala.collection.mutable.Queue(1,2,3)
    println(q)    // Queue(1, 2, 3)

    println(q += 4) // Queue(1, 2, 3, 4)
    println(q)   // Queue(1, 2, 3, 4)

    println(q += (5,6)) // Queue(1, 2, 3, 4, 5, 6)
    println(q)         // Queue(1, 2, 3, 4, 5, 6)

    println(q ++= List(7,8,9)) // Queue(1, 2, 3, 4, 5, 6, 7, 8, 9)
    println(q)                // Queue(1, 2, 3, 4, 5, 6, 7, 8, 9)

    q.enqueue(10)  // it returns nothing adds 7 to Queue
    println(q)    // Queue(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    q.enqueue(11,12)  // it returns nothing adds 7 to Queue
    println(q)      // Queue(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

    // get the element from the head of Queue

    val h1 = q.dequeue()

    println(h1) // 1
    println(q)  // Queue(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

    println(q.dequeue())  // 2
    println(q)           // Queue(3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

    // dequeueFirst: removes an element based on fitst occurance of predicate, return type will be Some or None
    // dequeueAll: removes all elements based on occurance of predicate, return type will be ArrayBuffer

    val h2 = q.dequeueFirst(x => x > 5)
    println(h2)             // Some(6)
    println(q)              // Queue(3, 4, 5, 7, 8, 9, 10, 11, 12)

    val h3 = q.dequeueFirst(x => x % 2 == 0)
    println(h3)             // Some(4)
    println(q)              // Queue(3, 5, 7, 8, 9, 10, 11, 12)

    val h4 = q.dequeueAll(x => x % 2 == 1)
    println(h4)            // ArrayBuffer(3, 5, 7, 9, 11)
    println(q)             // Queue(8, 10, 12)

    val h5 = q.dequeueAll(x => x > 20)
    println(h5)           // ArrayBuffer()
    println(q)            // Queue(8, 10, 12)



  }

}
