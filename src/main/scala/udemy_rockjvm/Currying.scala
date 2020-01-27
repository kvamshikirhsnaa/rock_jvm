package udemy_rockjvm

object Currying {
  def main(args: Array[String]): Unit = {

/*  val superFunction: (Int, (String, (Int => Boolean)) => Int) => (Int => Int) = ???
       new Function2[Int, Function2[String, Function1[Int, Boolean], Int], Function1[Int, Int]] {
         override def apply(x: Int, y: Function2[String, Function1[Int, Boolean], Int]) = new Function1[Int, Int] {
           override def apply(z: Int) = x + y("hello", (x: Int) => x % 2 == 0) + z
         }
       }  */


    // function that applies a function n times over a value x
    // nTimes(f, n, x)
    // nTimes(f, 3, x) = f(f(f(x)))

    def nTimes(f: Int => Int, n: Int, x: Int): Int = {
      if (n <= 0) x
      else nTimes(f, n - 1, f(x))
    }

    val plusOne = (x: Int) => x + 1
    println(nTimes(plusOne, 5, 0))
    println(nTimes(plusOne, 10, 10))


    // ntb(f, n) = x => f(f(f(...(x))))
    // increment10 = ntb(plusone, 10) = x => plusone(plusone.....(x))
    // val y = increment10(1)
    def nTimesBetter(f: Int => Int, n: Int): Int => Int = {
      if (n <= 0) (x: Int) => x
      else (x: Int) => nTimesBetter(f, n - 1)(f(x))
    }


    val res1 = nTimesBetter(plusOne, 5)
    println(res1)
    val res2 = res1(5)
    println(res2)


    // function with multiple parameter lists
    def curriedFormatter(c: String)(x: Double): String = {
      c.format(x)
    }

    val standardFormat: Double => String = curriedFormatter("%4.2f")
    val preciseFormat: Double => String = curriedFormatter("%10.8f")
    println(standardFormat(Math.PI))
    println(preciseFormat(Math.PI))


    // toCurry(f: (Int, Int) => Int) => (Int => Int => Int)
    // fromCurry(f: (Int => Int => Int) => (Int, Int) => Int

    def toCurry(f: (Int, Int) => Int): (Int => Int => Int) = {
      x => y => f(x,y)
    }

    def fromCurry(f: (Int => Int => Int)): (Int, Int) => Int = {
      (x, y) => f(x)(y)
    }


    val superAdder: (Int => Int => Int) = toCurry((a, b) => a + b)
    val add = superAdder(4)
    println(add)
    println(add(17))

    val simpleAdder: (Int, Int) => Int = fromCurry(a => b => a + b)
    println(simpleAdder(4, 17))

    // compose(f,g) => x => f(g(x))
    // andThen(f,g) => x => g(f(x))

/*
   def compose(f: Int => Int, g: Int => Int): Int => Int = {
      x => f(g(x))
    }

    def andThen(f: Int => Int, g: Int => Int): Int => Int = {
      x => g(f(x))
    }
*/

    def compose[A,B,T](f: A => B, g: T => A): T => B = {
      x => f(g(x))
    }

    def andThen[A,B,C](f: A => B, g: B => C): A => C = {
      x => g(f(x))
    }

    val add2 = (x: Int) => x + 2
    val times3 = (x: Int) => x * 3

    val composed = compose(add2, times3)
    val ordered = andThen(add2, times3)

    println(composed(4)) // 14  = (4 * 3 = 12 + 2 = 14)
    println(ordered(4))  // 18  = (4 + 2 = 6 * 3 = 18)











    }

}
