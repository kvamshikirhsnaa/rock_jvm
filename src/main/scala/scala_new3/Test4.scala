package scala_new3

object Test4 {
  def main(args: Array[String]): Unit = {

    // Finding Sum of Factorials of between 2 given numbers

    println(sumOfFact(2,4)) // 32

    println(sumOfFact(2,5)) // 152

    println(sumOfFact(2,20)) // 268040728


  }

  def sumOfFact(a: Int, b: Int): Int = {
    def fact(n: Int): Int = {
      def factR(n: Int, acc: Int): Int = {
        if (n < 1) acc
        else factR(n - 1, n * acc)
      }
      factR(n, 1)
    }
    def sumOfFactR(a: Int, acc: Int): Int = a match{
      case a if(a > b) => acc
      case a => sumOfFactR(a + 1, acc + fact(a))
    }
    sumOfFactR(a, 0)
  }



}
