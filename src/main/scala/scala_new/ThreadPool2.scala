package scala_new


import java.util.concurrent.{Callable,Executors, Future, ExecutorService, TimeUnit}
import scala.collection.JavaConverters._
import scala.collection.mutable._

class MyCallable2(lst: List[Int]) extends Callable[Int] {
  def call(): Int = {
    println(s"Current Thread Running: ${Thread.currentThread.getName}")
    val sum = lst.reduce(_ + _)
    println(s"Total sum of $lst ${Thread.currentThread.getName}: $sum")
    Thread.sleep(1000)
    println(s"Calculation takes 1 sec: ${Thread.currentThread.getName}")
    sum
  }
}


object ThreadPool2 {
  def main(args: Array[String]): Unit = {

    val s = System.currentTimeMillis()

    val numList = (1 to 100).toList
    val batchSize = 20

    val threadPool = Executors.newFixedThreadPool( 3 )

    val futureList = new ListBuffer[Future[Int]]

    val ind = numList.filter( x => x % batchSize == 0 )

    var start = 0
    println( "Job submission starts" )
    ind.foreach { x =>
      val lst = for (i <- start to x) yield i
      val lst2 = lst.toList
      start = x
      val callable = new MyCallable2(lst2)
      val result = threadPool.submit( callable )
      futureList += result
    }
    println( s"Job Submitted Successfully" )

    futureList.foreach{future =>
      while (!future.isDone) {
        Thread.sleep(500)
        println(s"Job is still running,${Thread.currentThread.getName} -> $future")
      }
      println(s"Result: ${future.get}")
    }

//    var newstart = 0
//    val callableList = ind.map{x =>
//      val lst = for(i <- newstart to x) yield i
//      val lst2 = lst.toList
//      newstart = x
//      val callable = new MyCallable2(lst2)
//      callable
//    }
//
//    val futureListNew = threadPool.invokeAll(callableList.asJava)

//    var totalSum = 0
//    futureListNew.forEach(future => {
//      while (!future.isDone) {
//        Thread.sleep(500)
//        println(s"Job is still running,${Thread.currentThread.getName} -> $future")
//      }
//      totalSum += future.get()
//      println(totalSum)
//    })

    threadPool.shutdown()

    println((System.currentTimeMillis - s)/1000)


  }
}
