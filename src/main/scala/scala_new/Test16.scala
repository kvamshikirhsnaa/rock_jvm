package scala_new

object Test16 {
  def main(args: Array[String]): Unit = {

// from stackoverflow
    def max(xs: List[Int]): Option[Int] = xs match {
      case Nil => None
      case List(x: Int) => Some(x)
     case x :: y :: rest => max( (if (x > y) x else y) :: rest )
    }

    println(max(List(1)))
    println(max(List(1,2)))
    println(max(List(1,2,6,8,9,5,7,11,3,4,10)))

    def maxnew[A <% Ordered[A]](xs: Traversable[A]): Option[A] = xs match {
      case i if i.isEmpty => None
      case i if i.size == 1 => Some(i.head)
      case i if (i collect { case x if x > i.head => x }).isEmpty => Some(i.head)
      case _ => maxnew(xs collect { case x if x > xs.head => x })
    }

    println(maxnew(List(1)))
    println(maxnew(List(1,2)))
    println(maxnew(List(1,2,6,8,9,5,7,19,3,4,10)))

    // on empty list max, head, etc throw error
    // headOption will work


  }
}
