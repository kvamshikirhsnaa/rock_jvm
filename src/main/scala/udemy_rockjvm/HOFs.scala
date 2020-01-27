package udemy_rockjvm

object HOFs {
  def main(args: Array[String]): Unit = {

    val lst = List(1,2,3)
    println(lst)
    println(lst.head)
    println(lst.tail)

    // map
    println(lst.map(x => x + 1))
    println(lst.map(x => x + " is a number"))

    // filter
    println(lst.filter(x => x % 2 == 0))

    // flatMap
    val toPair = (x: Int) => List(x, x + 1)
    println(lst.flatMap(toPair))

    // print all combinations b/w two lists
    val numbers = List(1,2,3,4)
    val chars = List('a', 'b', 'c', 'd')
    val colors = List("black", "white")
    val combinations = (x: Int) => chars.map(a => "" + a + x)
    val combinations2 = (x: Int) => chars.flatMap(a => colors.map(b => "" + a + x + "_" + b))
    // iterating
    println(numbers.flatMap(combinations))
    println(numbers.flatMap(combinations2))

    // foreach
    println(lst.foreach(println))

    // for-comprehensions
    val forCombinations = for {
      c <- chars
      n <- numbers if (n % 2 == 0)
      color <- colors
    } yield "" + c + n + "_" + color

    println(forCombinations)

    for {
      n <- numbers
    } println(n)

    // syntax overload
    lst.map {x =>
      x * 2
    }


    // 1. MyList supports for comprehensions?
    // 2. A small collection of at most ONE element - Maybe[+T]
    //   - map, flatMap, filter




  }

}
