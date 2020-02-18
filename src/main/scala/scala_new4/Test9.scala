package scala_new4

import scala.annotation.tailrec
import scala.util.Random

object Test9 extends App {

  def isPrime(n: Int): Boolean = {
    val s = Math.sqrt(n).toInt
    @tailrec
    def isPrimeTailrec(k: Int): Boolean = {
      if (k > s) true
      else (n % k != 0) && isPrimeTailrec(k + 1)
    }
    isPrimeTailrec(2)
  }

  def getPrimes(n: Int): List[Int] = {
    val odds =  2 :: (3 to n by 1).toList
    odds.filter(isPrime)
  }


  def decomposePrime(n: Int): List[Int] = {
    @tailrec
    def decomposePrimeTailrec(k: Int, curr: Int, acc: List[Int]): List[Int] = {
      if (curr > Math.sqrt(k)) k :: acc
      else if (k % curr == 0) decomposePrimeTailrec(k / curr, curr , curr :: acc)
      else decomposePrimeTailrec(k, curr + 1, acc)
    }
    decomposePrimeTailrec(n,2, List.empty)
  }

  def approximatePi(n: Int): Double = {
    val random = new Random(System.currentTimeMillis())
    val randomPtsInCircle = (1 to n).map{ _ =>
      val x = random.nextDouble()
      val y = random.nextDouble()

      x * x + y * y
    }.count(distance => distance < 1)

    randomPtsInCircle * 4.0 / n
  }



  println(getPrimes(50))  // List(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47)
  println(decomposePrime(16))  // List(2, 2, 2, 2)
  println(decomposePrime(121))  // List(11, 11)
  println(decomposePrime(547))  // List(547)
  println(decomposePrime(2003))  // List(2003)
  println(decomposePrime(2002))  // List(13, 11, 7, 2)


  println(Math.PI)   // 3.141592653589793
  println(approximatePi(10))  // 3.6
  println(approximatePi(100))  // 3.04
  println(approximatePi(1000))  // 3.164
  println(approximatePi(10000))  // 3.1836
  println(approximatePi(100000))  // 3.14428
  println(approximatePi(1000000)) // 3.142524
  println(approximatePi(10000000)) // 3.1407944



}
