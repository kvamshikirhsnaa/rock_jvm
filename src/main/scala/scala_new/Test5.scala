package scala_new



object Test5 {
  def main(args: Array[String]): Unit = {

    println( primesUnder( 5 ) )
    println(primes(20))
    println(getPrimeUnder(3))



  }
  def primesUnder(n: Int): List[Int] = {
    require(n >= 2)

    def rec(i: Int, primes: List[Int]): List[Int] = {
      if (i >= n) primes
      else if (prime(i, primes)) rec(i + 1, i :: primes)
      else rec(i + 1, primes)
    }

    rec(2, List()).reverse
  }

  def prime(num: Int, factors: List[Int]): Boolean = factors.forall(num % _ != 0)


  // returns the list of primes below `number`
  def primes(number: Int): List[Int] = {
    number match {
      case a
        if (a <= 3) => (2 to a).toList
      case x => (2 to x - 1).filter(b => isPrime(b)).toList
    }
  }

  // checks if a number is prime
  def isPrime(number: Int): Boolean = {
    number match {
      case x => Nil == {
        (2 to math.sqrt(x).toInt).filter(y => x % y == 0)
      }
    }
  }

  def getPrimeUnder(n: Int) = {
    require(n >= 2)
    val ol = 3 to n by 2 toList // oddList
    def pn(ol: List[Int], pl: List[Int]): List[Int] = ol match {
      case Nil => pl
      case _ if pl.exists(ol.head % _ == 0) => pn(ol.tail, pl)
      case _ => pn(ol.tail, ol.head :: pl)
    }
    pn(ol, List(2)).reverse
  }



  def findPrime(i : Int) : List[Int] = i match {
    case 2 => List(2)
    case _ => {
      val primeList = findPrime(i-1)
      if(isPrimenew(i, primeList)) i :: primeList else primeList
    }
  }

  def isPrimenew(num : Int, prePrimes : List[Int]) : Boolean = prePrimes.forall(num % _ != 0)




}
