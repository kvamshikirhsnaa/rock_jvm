package udemy_rockjvm

import scala.util.Random

object Options {
  def main(args: Array[String]): Unit = {

    val myFirstOption: Option[Int] = Some(3)
    val noOption: Option[Int] = None
    println(myFirstOption)
    println(noOption)

    // Options are useful to deal with unsafe APIs
    def unsafeMethod(): String = null
    // val result = Some(unsafeMethod()) // -> Some(null), Wrong declaration, always Some() should have value
    val result = Option(unsafeMethod())
    println(result)  // None

    // Chained methods
    def backupMethod(): String = "A valid result"
    val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))
    println(chainedResult)   // Some(A valid method)

    // DESIGN Unsafe APIs
    def betterUnsafeMethod(): Option[String] = None
    def betterBackupMethod(): Option[String] = Some("A valid result")
    val betterChainedResult = betterUnsafeMethod().orElse(betterBackupMethod())
    println(betterChainedResult)  // Some(A valid result)


    // functions on Options
    println(myFirstOption.isEmpty)
    println(myFirstOption.get) // Unsafe, cuz it trying to get value, if the value is None throws error
   // println(noOption.get)  // java.util.NoSuchElementException: None.get
    println(noOption.getOrElse("no value")) // no value, this is safe


    // map, flatMap, filter
    println(myFirstOption.map(x => x * 2)) // Some(6)
    println(myFirstOption.filter(x => x % 2 == 0)) // None
    println(myFirstOption.filter(x => x % 3 == 0)) // Some(3)
    println(myFirstOption.flatMap(x => Option(x * 4))) // Some(12)


    // assignment
    val config: Map[String, String] = Map(
      // fetched from else where
      "host" -> "176.45.36.1",
      "port" -> "80"
    )

    class Connection {
      def connect = "Connected" // connect to some server
    }

    object Connection {
      val random = new Random(System.nanoTime())

      // method written by some1
      def apply(host: String, port: String): Option[Connection] = {
        if (random.nextBoolean()) Some(new Connection)
        else None
      }
    }

    // try to establish connection, if so - print the connect method

    val host = config.get("host")
    val port = config.get("port")
 /*
     if (h != null)
        if (p != null)
           return Connection.apply(h,p)
     else null
*/
    val connection = host.flatMap(h => port.flatMap(p => Connection.apply(h,p)))

/*
    if (c != null)
       return c.connect
    else null
*/
    val connectionStatus = connection.map(c => c.connect)

    // if connectionStatus == null println(None) else print (Some(connectionStatus.get))
    println(connectionStatus)  // if connection establish returns Some(Connected) else None

    // if connectionStatus != null
    //       println(connectionStatus) , otherwise do not print anything
    connectionStatus.foreach(println)  // if connection establish will return "Connected" else nothing



    // in short
    val connectionStatus2 = config.get("host").
      flatMap(h => config.get("port").
      flatMap(p => Connection(h,p)).
      map(c => c.connect))

    println(connectionStatus2)
    println(connectionStatus2.foreach(println))

    config.get("host").
      flatMap(h => config.get("port").
        flatMap(p => Connection(h,p)).
        map(c => c.connect)).
      foreach(println)


    // for-comprehensions
    val forConnectionStatus = for {
      host <- config.get("host")
      port <- config.get("port")
      con <- Connection(host, port)
    } yield con.connect

    println(forConnectionStatus)
    println(forConnectionStatus.foreach(println))
  }

}
