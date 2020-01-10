package udemy_rockjvm

object Functions {
  def main(args: Array[String]): Unit = {

    // define a function which takes Int and returns another function which takes an int and returns an int
    // Function1[Int, Function1[Int,Int]]

    val superAdder: Function1[Int, Function1[Int,Int]] = new Function1[Int, Function1[Int,Int]] {
      override def apply(x: Int): Function1[Int, Int] = new Function1[Int, Int] {
        override def apply(y: Int): Int = x + y
      }
    }

    val adder = superAdder(3)
    println(adder) // returns a function, takes int as argument and returns int
    println(adder(4))
    println(superAdder(4)(5)) // Currying



  }

}
