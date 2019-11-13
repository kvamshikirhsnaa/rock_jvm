package scala_new

import scala.collection.mutable.ListBuffer

class MyRunnable3(list: List[Int]) extends Runnable {
  def run(): Unit = {
    println(s"Current Thread Running: ${Thread.currentThread.getName}")
    val sum = list.reduceLeft((a,b) => a + b)
    println(s"Total sum of $list ${Thread.currentThread.getName}: $sum")
    Thread.sleep(1000)
    println(s"Calculation takes 1 sec: ${Thread.currentThread.getName}")

  }
}

object Threads3 {

  def main(args: Array[String]): Unit = {
    val numList = (1 to 100).toList
    val batchSize = 20

    val ind = numList.filter(x => x % batchSize == 0)

    var start = 0
    ind.foreach {x =>
      val lst = for (i <- start to x) yield i
      val lst2 = lst.toList
      start = x
      val runnable = new MyRunnable3(lst2)
      val th = new Thread(runnable)
      // th.setName(s"Child${list.head}")
      th.start()

    }
    }

}