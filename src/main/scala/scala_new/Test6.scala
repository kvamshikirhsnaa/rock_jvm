package scala_new

object Test6 {
  def main(args: Array[String]): Unit = {

    println(sum((1 to 100).toList))
    println(sumnew((1 to 100).toList))

  }

  def sum(lst: List[Int]): Int = {
    if (lst.length == 1) return lst.head
    else lst.head + sum(lst.tail)
  }

  def sumnew(lst: List[Int]): Int = {
    def sumtail(acc: Int, lst: List[Int]): Int = {
      if (lst.length == 1) acc + lst.head
      else sumtail( acc + lst.head, lst.tail )
    }
    sumtail(0, lst)
  }




}
