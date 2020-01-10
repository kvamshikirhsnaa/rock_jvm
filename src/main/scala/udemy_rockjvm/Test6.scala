package udemy_rockjvm

import scala.annotation.tailrec

object Test6 {
  def main(args: Array[String]): Unit = {


    println(isPrime(9))
    println(isPrime(19))
    println(isPrime(12351))
    println(isPrime(235472657))
    println(isPrime(235745731))
    println(isPrime(23574571))

    println(isPrime2(15))
/*    println(isPrime2(235472657))
    println(isPrime2(235745731))
    */
    println(fibo(5))
    println(fibo(8))

  }

  // here isPrimeUntil is tail recursion,cuz of && is short hand notation
  def isPrime(n: Int): Boolean = {
    @tailrec
    def isPrimeUntil(t: Int): Boolean = {
      if (t <= 1) true
      else n % t != 0 && isPrimeUntil(t - 1)
    }
    isPrimeUntil(n/2)
  }



  // here isPrimeUntil is recursion,cuz of & is not short hand notation
  def isPrime2(n: Int): Boolean = {
    def isPrimeUntil(t: Int): Boolean = {
      if (t <= 1) true
      else n % t != 0 & isPrimeUntil(t - 1)
    }
    isPrimeUntil(n/2)
  }

  def fibo(n: Int): Int = {
    @tailrec
    def fiboRec(s: Int, last: Int, nextToLast: Int): Int = {
      if (n <= s ) last
      else fiboRec(s + 1, last + nextToLast, last)
    }
    fiboRec(2,1,1)
  }







}
