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



    // define anonymous/lambda function

    val doubler = (x: Int) => x * 2
    println(doubler(9))

    val tripler: Int => Int = x => x * 3
    println(tripler(6))


    // multiple params in lambda

    val addernew: (Int, Int) => Int = (x, y) => x + y
    println(addernew(4,5))

    val addernew2 = (x: Int, y: Int) => x + y
    println(addernew2(2,3))


    // no params
    val jstDoSmthng: () => Int = () => 3
    println(jstDoSmthng) // returns ananymous function
    println(jstDoSmthng()) // returns 3


    // curly braces with lambda
    val stringToInt = { (str: String) =>
      str.toInt
    }
    println(stringToInt("5") + 6)


    val superAdderNew: Int => Int => Int = x => y => (x + y)
    val superAdderNew2 = (x: Int) => (y: Int) => (x + y)
    println(superAdderNew(3)) // returns function
    println(superAdderNew2(4)) // returns function

    println(superAdderNew(3)(4))
    println(superAdderNew2(5)(4))



  }

}

