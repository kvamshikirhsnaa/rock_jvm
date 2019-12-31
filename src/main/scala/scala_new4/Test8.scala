package scala_new4


class Counter(val count: Int = 0) {
  def incr: Counter = {
    println("incrementing")
    new Counter(count + 1)
  }

  def decr: Counter = {
    println("decrementing")
    new Counter(count - 1)
  }

  def incr(n: Int): Counter = {
    if (n <= 0) this
    else incr.incr(n - 1)
  }

  def decr(n: Int): Counter = {
    if (n <= 0) this
    else decr.decr(n - 1)
  }

  println(s"current count is: $count")


}

object Test8 {
  def main(args: Array[String]): Unit = {


    val c1 = new Counter

    println()
    println("Incrementing 1 value")
    println("---------------------")
    println(c1.incr.count)

    println()
    println("Incrementing 3 times")
    println("---------------------")
    println(c1.incr.incr.incr.count)

    println()
    println("Incrementing 10 times")
    println("---------------------")
    println(c1.incr(10).count)

  }


}
