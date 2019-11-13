package scala_new

object Test8 {
  def main(args: Array[String]): Unit = {

    val lst = List(2,4,6,8,9,10,12,13,15,16)

    // drop, dropRight, dropWhile

    println(lst.drop(2))
    println(lst.drop(4))
    println(lst.drop(15))

    println(lst.dropRight(2))
    println(lst.dropRight(4))
    println(lst.dropRight(15))

    println(lst.dropWhile(x => x == 2))
    println(lst.dropWhile(x => x == 4)) // drops nothing
    println(lst.dropWhile(x => x < 8 ))
    println(lst.dropWhile(x => x > 8 )) // drops nothing
    println(lst.dropWhile(x => x % 2 == 0))
    println(lst.dropWhile(x => x % 2 == 1)) // drops nothing

    // take, takeRight, takeWhile

    println(lst.take(2))
    println(lst.take(4))
    println(lst.take(15))

    println(lst.takeRight(2))
    println(lst.takeRight(4))
    println(lst.takeRight(15))

    println(lst.takeWhile(x => x == 2))
    println(lst.takeWhile(x => x == 4)) // takes nothing
    println(lst.takeWhile(x => x < 8 ))
    println(lst.takeWhile(x => x > 8 )) // takes nothing
    println(lst.takeWhile(x => x % 2 == 0))
    println(lst.takeWhile(x => x % 2 == 1)) // takes nothing



  }

}
