package udemy_rockjvm

import scala.util.{Failure, Random, Success, Try}

object HandlingFailure {
  def main(args: Array[String]): Unit = {

    // create Success and Failure
    val aSuccess = Success(3)
    val aFailure = Failure(new RuntimeException("SUPER FAILURE"))
    println(aSuccess)   // Success(3)
    println(aFailure)  // Failure(java.lang.RuntimeException: SUPER FAILURE)

    // Try, will catch unsafe methods from throwing error
    def unsafeMethod(): String = throw new RuntimeException("NO STRING FOR YOU...")

    // Try objects via apply method
    val potentialFailure = Try(unsafeMethod())
    println(potentialFailure)  // Failure(java.lang.RuntimeException: NO STRING FOR YOU...)

    // syntax sugar
    val anothoerPotentialFailure = Try {
      // code that might throw error
    }

    // utilities
    println(potentialFailure.isSuccess) // false
    println(potentialFailure.isFailure)  // true

    // orElse
    def backupMethod(): String = "A valid result"
    //val fallbackTry = unsafeMethod().orElse(backupMethod())
    // println(fallbackTry) //unsafe, throw error, java.lang.RuntimeException: NO STRING FOR YOU...

    val falbackTryNew = Try(unsafeMethod()).orElse(Try(backupMethod()))
    println(falbackTryNew) // safe, Success(A valid result)

    // if you design API
    def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException)
    def betterBackupMethod(): Try[String] = Success("A valid result")
    val betterFallbackTry = betterUnsafeMethod().orElse(betterBackupMethod())
    println(betterFallbackTry)  // Success(A valid result)

    // whenever code might return null use Option,
    // whenever code might throw exceptions use Try

    // Try also has map, flatMap, filter functions
    println(aSuccess.map(x => x * 2))  // Success(6)
    println(aSuccess.filter(x => x % 2 == 0)) // Failure(java.util.NoSuchElementException: Predicate does not hold for 3)
    println(aSuccess.filter(x => x % 3 == 0)) // Success(3)
    println(aSuccess.flatMap(x => Success(x * 3)))  // Success(9)

/*
    Exercise


*/

    val host = "localhost"
    val port = "8080"
    def renderHTML(page: String) = println(page)

    class Connection {
      def get(url: String): String = {
        val random = new Random(System.nanoTime())

        if (random.nextBoolean()) "<html> .... </html>"
        else throw new RuntimeException("Connection Interrupted")
      }

      def getSafe(url: String): Try[String] = Try(get(url))
    }

    object HttpService {
      val random = new Random(System.nanoTime())

      def getConnection(host: String, port: String): Connection = {
        if (random.nextBoolean()) new Connection
        else throw new RuntimeException("port already occupied")
      }

      def getSafeConnection(host: String, port: String): Try[Connection] = {
        Try(getConnection(host, port))
      }
    }

    // if you get html page from Connection, print it to the console, i.e, call renderHTML method

    val possibleConncetion = HttpService.getSafeConnection(host, port)
    val possibleHTML = possibleConncetion.flatMap(con => con.getSafe("/home"))
    possibleHTML.foreach(x => renderHTML(x))


    //shorthand-version
    HttpService.getSafeConnection(host,port).
      flatMap(con => con.getSafe("/home")).
      foreach(renderHTML)


    // for-comprehension version

    for {
      connection <- HttpService.getSafeConnection(host, port)
      html <- connection.getSafe("/home")
    } renderHTML(html)









  }

}
