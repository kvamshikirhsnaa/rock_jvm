package scala_new



class MyRunnable4(lst: List[Int]) extends Runnable {

  def run(): Unit = {
    println(s"Current Running  Thread: ${Thread.currentThread.getName}")
    val res = lst.reduce(_ + _)
    println(s"Total sum of $lst ${Thread.currentThread.getName}: $res")
    Thread.sleep(1000)
    println(s"Calculation takes 1 sec: ${Thread.currentThread.getName}")
  }
}

object Thread4 {
  def main(args: Array[String]): Unit = {
    val numList = (1 to 100).toList
    val batchSize = 10

    val indexes = numList.filter(x => x % batchSize == 0)

    var start = 0
    indexes.foreach {x =>
      val lst = for (i <- start to x) yield i
      val lst2 = lst.toList
      start = x
      val runnable = new MyRunnable4(lst2)
      val th = new Thread(runnable)
      th.start()

    }

  }

}
