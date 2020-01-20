package udemy_rockjvm

object Exceptions {
  def main(args: Array[String]): Unit = {


    // 1. Throwing Exceptions
    val x: String = null
    //    println(x.length)   // throws NullPointerException

    //    val aWeirdValue: String = throw new NullPointerException  // throws NullPointerException


    // 2. Catching Exceptions
    // try,catch and finally also an expresssion
    def getInt(withExceptions: Boolean): Int = {
      if (withExceptions) throw new RuntimeException("no int for you")
      else 28
    }

    val potentialFail = try {
      getInt(true)
    } catch {
      case e: RuntimeException => 29
    } finally {
      // code that will execute NO MATTER WHAT
      // finally is an optional, it does't influence the return type, jst use for side effect
      println("finally")
    }

    println(potentialFail)


    // 3. how to define own Exceptions

    class MyException extends Exception

    val exception = new MyException

    //    throw exception   // throws Exceptions$MyException$1

    // throwing OOM ERROR
    //    val a = Array.ofDim(Int.MaxValue)    //java.lang.OutOfMemoryError: Requested array size exceeds VM limit

    class OverflowException extends RuntimeException
    class UnderflowException extends RuntimeException
    class MathCalculationException extends RuntimeException("Division By Zero Exception")

    object PocketCalculator {
      def add(x: Int, y: Int): Int = {
        val res = x + y
        if (x > 0 && y > 0 && res <= 0) throw new OverflowException
        else if (x < 0 && y < 0 && res >= 0) throw new UnderflowException
        else res
      }
      def subtract(x: Int, y: Int): Int = {
        val res = x - y
        if (x > 0 && y < 0 && res <= 0) throw new OverflowException
        else if (x < 0 && y > 0 && res >= 0) throw new UnderflowException
        else res
      }
      def multiply(x: Int, y: Int): Int = {
        val res = x * y
        if (x > 0 && y > 0 && res <= 0) throw new OverflowException
        else if (x < 0 && y < 0 && res <= 0) throw new OverflowException
        else if (x > 0 && y < 0 && res >= 0) throw new UnderflowException
        else if (x < 0 && y > 0 && res >= 0) throw new UnderflowException
        else res
      }

      def divide(x: Int, y: Int): Int = {
        if (y == 0) throw new MathCalculationException
        else x / y
      }
    }

    val calc = PocketCalculator
    println(calc.add(50,25))
    //    println(calc.add(Int.MaxValue, 1))
    //    println(calc.add(Int.MinValue, -1))
    //    println(calc.add(Int.MinValue, Int.MinValue))

    println(calc.subtract(100,50))
    //    println(calc.subtract(Int.MinValue, 1))
    //    println(calc.subtract(Int.MaxValue, -1))

    println(calc.multiply(20,50))
    //    println(calc.multiply(Int.MinValue, -100000000))

    println(calc.divide(50,5))
    //    println(calc.divide(5,0))





  }

}

