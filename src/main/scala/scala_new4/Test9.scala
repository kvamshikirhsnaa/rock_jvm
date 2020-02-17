package scala_new4

import scala.annotation.tailrec

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



  println(getPrimes(50))  // List(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47)
  println(decomposePrime(16))  // List(2, 2, 2, 2)
  println(decomposePrime(121))  // List(11, 11)
  println(decomposePrime(547))  // List(547)
  println(decomposePrime(2003))  // List(2003)
  println(decomposePrime(2002))  // List(13, 11, 7, 2)

}
