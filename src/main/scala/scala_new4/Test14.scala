package scala_new4

object Test14 extends App {

  def isPrime(n: Int) : Boolean = {
    def isPrimeTailrec(t: Int, isStillPrime: Boolean): Boolean = {
      if (!isStillPrime) false
      else if (t <= 1) true
      else isPrimeTailrec(t - 1, n % t != 0 )
    }
    val t = Math.sqrt(n).toInt
    isPrimeTailrec(t, true)
  }

  println(isPrime(9)) // false
  println(isPrime(7)) // true
  println(isPrime(101))  // true
  println(isPrime(2003))  // true


  // finding longest adjacent array sum
  def findLargeArrSum(lst: List[Int]): Int = {
    val sub_arrs = for {
      i <- Range(0, lst.length)
      j <- Range(i + 1, lst.length + 1)
    } yield lst.slice(i, j)
    println(sub_arrs)
    implicit val lstOrdering: Ordering[List[Int]] = Ordering.fromLessThan((x, y) => x.sum < y.sum)
    val max_sum_arr = sub_arrs.sorted.last
    println("max sub array")
    println("--------------")
    println(max_sum_arr)  // List(2, 5, -4, 6)
    max_sum_arr.sum
  }

  val lst = List(1,-3,2,5,-4,6)
  println(findLargeArrSum(lst)) // 9,   List(2, 5, -4, 6)





}
