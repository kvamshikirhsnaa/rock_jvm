package scala_new

import java.util.concurrent.{Callable,Executors, ExecutorService, Future, TimeUnit}

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

class MyCallable(list: List[Int]) extends Callable[Int] {
  def call(): Int = {
    println(s"Current Thread Running: ${Thread.currentThread.getName}")
    val sum = list.reduce(_ + _)
    println(s"Total sum of $list ${Thread.currentThread.getName}: $sum")
    Thread.sleep(1000)
    println(s"Calculation takes 1 sec: ${Thread.currentThread.getName}")
    sum
  }
}

object ThreadsPool {

  def createSubList(numList: List[Int], batchSize: Int): List[List[Int]] = {
    val totalSubListSize = Math.ceil(numList.size/batchSize.toFloat).toInt
    val listOfSubList = new ListBuffer[List[Int]]

    for (i <- 0 to totalSubListSize - 1){
      var subList: List[Int] = null
      if (i < totalSubListSize) { // process everything except last batch
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

    val threadPool = Executors.newFixedThreadPool(3)

    //process1:
//    val futureList = new ListBuffer[Future[Int]]
//
//    println(s"Job Submission starts")
//    subSubList.map{list =>
//      val callable = new MyCallable(list)
//      val result = threadPool.submit(callable)  // submitting future task
//      futureList += result
//    }
//    println(s"Job Submitted Successfully")
//
//    futureList.foreach{future =>
//      while (!future.isDone) {
//        Thread.sleep(500)
//        println(s"Job is still running,${Thread.currentThread.getName} -> $future")
//      }
//      println(s"Result: ${future.get()}")
//    }


    //process2:
    val callableListNew = subSubList.map{list =>
      val callable = new MyCallable(list)
      callable
    }

    val futureListNew = threadPool.invokeAll(callableListNew.asJava)
    var totalSum = 0

//    futureListNew.forEach(future =>
//    {
//      while (!future.isDone) {
//        Thread.sleep(500)
//        println(s"Job is still running,${Thread.currentThread.getName} -> $future")
//      }
//      totalSum += future.get()
//    })

    threadPool.shutdown()

  }
}







