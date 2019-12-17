package scala_new4

object Test6 {
  def main(args: Array[String]): Unit = {


    println(isPrime(19))
    println(isPrime(12351))
    println(isPrime(235472657))
    println(isPrime(235745731))
    println(isPrime(23574571))

    println(isPrime2(15))
    println(isPrime2(235472657))
    println(isPrime2(235745731))

  }

  // here isPrimeUntil is tail recursion,cuz of && is short hand notation
  def isPrime(n: Int): Boolean = {
    def isPrimeUntil(t: Int): Boolean = {
      if (t <= 1) true
      else n % t != 0 && isPrimeUntil(t - 1)
    }
    isPrimeUntil(n/2)
  }



  // here isPrimeUntil is tail recursion,cuz of & is not short hand notation
  def isPrime2(n: Int): Boolean = {
    def isPrimeUntil(t: Int): Boolean = {
      if (t <= 1) true
      else n % t != 0 & isPrimeUntil(t - 1)
    }
    isPrimeUntil(n/2)
  }





}
