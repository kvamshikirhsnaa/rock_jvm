package scala_new3

object Test5 {
  def main(args: Array[String]): Unit = {

    println(sumOfSqrs(x => x * x, 2, 4)) // 29

    println(sumOfSqrs(x => x * x, 2, 5)) // 54

    println(newsample(sample, 1, 10)) // 11

    println(sumOfSqrs2(x => x * x, 2, 5)) // 54


    }

  // Sum of Squares of 2 given numbers usinf recursion
  def sumOfSqrs(f: Int => Int, a: Int, b: Int): Int = {
    if (a > b) 0
    else f( a ) + sumOfSqrs( f, a + 1, b )
  }

  val sample = (a: Int, b: Int) => a + b

  val sample2:(Int,Int) => Int = (a, b) => a + b

  def newsample(f: (Int, Int) => Int, a: Int, b: Int) = f(a,b)


  // Sum of Squares of 2 given numbers using tail recursion
  def sumOfSqrs2(f: Int => Int, a: Int, b: Int): Int = {
    def sumOfSqrs2R(f: Int => Int, a: Int, acc: Int): Int = {
      if (a > b) acc
      else sumOfSqrs2R( f, a + 1, acc + f(a))
    }
    sumOfSqrs2R(f,a, 0)
  }


}


