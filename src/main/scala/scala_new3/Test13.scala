package scala_new3

object Test13 {
  def main(args: Array[String]): Unit = {

    // Stream: it is lazy version of List. since the Streams are
    // computed lazily, it's size can be huge

    val str = (1 to 10).toStream

    println(str)  // Stream(1, ?), it will give first value won't compute what is last value
                  // whenever any computation done it perform lazily

    println(str.head)         // 1
    println(str.headOption)   // Some(1)
    println(str.tail)         // Stream(2, ?)
    println(str.last)         // 10
    println(str.lastOption)   // Stream(2, ?)

    val sqrs = str.map(x => x * 2)

    println(sqrs)      // Stream(2, ?)

    println(sqrs(5))   // 12, it computed values of to 5th index which is 12

    println(sqrs)      // Stream(2, 4, 6, 8, 10, 12, ?)
    // once it computes next time when we call it give results of computed and keep lazy remaining

    // appending an element to Stream, creates new Stream cuz Streams are immutable
    val appele = sqrs :+ 22

    println(appele)   // Stream(2, ?)

    println(appele.last)   // 22

    // prepending an element to Stream, creates new Stream cuz Streams are immutable
    val preele = 0 +: sqrs

    println(preele)    // Stream(0, ?)

    // appending a collection to Stream, creates new Stream cuz Streams are immutable

    val applst = sqrs ++ List(24,26,28)

    println(applst)         //  Stream(2, ?)

    println(applst.last)    //  28

    // prepending a collection to Stream, creates new Stream cuz Streams are immutable

    val prelst = List(0,1) ++: sqrs

    println(prelst)        // Stream(0, ?)

    val fil = sqrs.filter(x => x > 10)

    println(fil)          // Stream(12, ?)


    // force: used to get all the Stream values

    val strres = sqrs.force

    println(strres)    // Stream(2, 4, 6, 8, 10, 12, 14, 16, 18, 20)


    // Streams can also create like below

    val str2 = 1 #:: 2 #:: 3 #:: 4 #:: 5 #:: Stream.empty[Int]

    println(str2)         // Stream(1, ?)





  }

}
