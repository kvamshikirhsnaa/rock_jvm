package scala_new

object Test4 {
  def main(args: Array[String]): Unit = {

    println(fibonacci(8))
    println(fibo(8))
    println(fiboNew(8))


  }

  // using recursion
  def fibonacci(n: BigInt): BigInt = {

    if (n <= 1) n
    else fibonacci(n - 1) + fibonacci(n - 2)
  }

  //using traditional
  def fibo(n: Int): Int = {
    var first = 0
    var second = 1
    var num = n
    while(num >= 1) {
      val result = first + second
      first = second
      second = result
      num = num - 1
    }
    return first

  }

  // using tail recursion

  def fiboNew(n: Int): Int = {
    def fibotail(n: Int, a: Int, b: Int): Int = {
      if (n < 1) a
      else fibotail(n - 1 , b, a + b)
    }
    fibotail(n, 0 , 1)
  }


}
