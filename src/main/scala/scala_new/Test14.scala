package scala_new

object Test14 {
  def main(args: Array[String]): Unit = {

    val lst = List("aaa","aaaa", "aaaaa", "bbb", "bbbb", "bbbb")

    val res = lst.groupBy {x =>
      if (x.size < 4) null
      else x.charAt(3)
    }

    println(res)

    val res1 = lst.groupBy(x => x.length)

    println(res1)

    val lst2 = List(1,2,3,4,5,6,7,8,9,10)

    val res3 = lst2.groupBy(x => if (x % 2 == 0) "even" else "odd")

    println(res3)


    val ls = List()
    val lstnew = List(1,2,3,4,5,6,7,8,9,10)

    val iter = lstnew.iterator

    // hasDefiniteSize: tests whether the collection has finite size
    // Returns false for a Stream or Iterator

    println(ls.hasDefiniteSize)
    println(lstnew.hasDefiniteSize)
    println(iter.hasDefiniteSize)
  }

}
