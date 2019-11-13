package scala_new

import scala.collection.mutable.ListBuffer

class MyRunnable2(list: List[Int]) extends Runnable {
  def run(): Unit = {
    println(s"Current Thread Running: ${Thread.currentThread.getName}")
    val sum = list.reduceLeft((a,b) => a + b)
    println(s"Total sum of $list ${Thread.currentThread.getName}: $sum")
    Thread.sleep(1000)
    println(s"Calculation takes 1 sec: ${Thread.currentThread.getName}")

  }
}

object Threads2 {

  def createSubList(numList: List[Int], batchSize: Int): List[List[Int]] = {
    val totalSubListSize = Math.ceil(numList.size/batchSize.toFloat).toInt
    val listOfSubList = new ListBuffer[List[Int]]

    for (i <- 0 to (totalSubListSize - 1)){
      var subList: List[Int] = null
      if (i < totalSubListSize) {
        subList = numList.slice(i * batchSize, (i + 1) * batchSize)
      }
      //else subList = numList.slice(i * batchSize, numList.length)
      listOfSubList += subList
    }
    listOfSubList.toList
  }

  def main(args: Array[String]): Unit = {
    val numList = (1 to 100).toList
    val batchSize = 20
    val subSubList = createSubList(numList, batchSize)
    //println(subSubList)


    subSubList.foreach { list => {
      val runnable = new MyRunnable2(list)
      val th = new Thread(runnable)
     // th.setName(s"Child${list.head}")
      th.start()
    }
    }


}
}






//val a = (1 to 100).toList
//println(a.slice(90,150)) // slice function won't throw arrayIndexOutOfBoundException,
//                         even if the index is not avail
//
//val z = "helloworld"
//println(z.slice(1,25))


// printing 1 to 100 , summing 10 numbers as batch
//var sum = 0
//for (i <- 1 to 100) {
//  if (i % 10 == 0) {
//  sum = sum + i
//  println( sum )
//  sum = 0
//} else sum = sum + i
//}
