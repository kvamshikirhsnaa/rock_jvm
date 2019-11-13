
class MyThread extends Thread {
  override def run(): Unit = {
    println(s"Current Thread Running ${Thread.currentThread.getName}")
    (1 to 10).foreach(num => {println(s"working $num"); Thread.sleep(1000)})
    println(s"Thread executed successfully ${Thread.currentThread.getName}")
  }
}

class MyRunnable extends Runnable {
  def run(): Unit = {
    println(s"Current Thread Running ${Thread.currentThread.getName}")
    (11 to 20).foreach(num => {println(s"working $num"); Thread.sleep(1000)})
    println(s"Thread executed successfully ${Thread.currentThread.getName}")

  }
}

// we can create threads using either Thread class or Runnable interface
// Runnable is interface in java, in scala we can say trait
// in that Run method is abstract so we dont need to use override key word
// where as in Thread class Run method is concrete method so we need to
// use override keyword


object Threads {
  def main(args: Array[String]): Unit = {
    println(s"main() starts: ${Thread.currentThread}")
    val th1 = new MyThread
    val runnable = new MyRunnable
    th1.setName("child1")
    val th2 = new Thread(runnable, "child2")
    th1.start()
    th2.start()
    Thread.sleep(5000)
    println(s"Program over: ${Thread.currentThread.getName}")

  }
}