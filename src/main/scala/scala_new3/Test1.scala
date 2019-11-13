package scala_new3

object Test1 {
  def main(args: Array[String]): Unit = {

    // Finding duplicates in a list

    val lst = List(1,1,2,3,1,4,2,5,6,5,7,9,8,2,3,5,7,2,7,5,3,1)

    // approach1(simple way)

    val dupes = lst.diff(lst.distinct).distinct
    println(dupes) // List(1, 2, 5, 3, 7)

    // approach2

    val grp = lst.groupBy(identity)
    println(grp)
    // Map(5 -> List(5, 5, 5, 5), 1 -> List(1, 1, 1, 1), 6 -> List(6), 9 -> List(9), 2 -> List(2, 2, 2, 2),
    // 7 -> List(7, 7, 7), 3 -> List(3, 3, 3), 8 -> List(8), 4 -> List(4))

    val dupesCnt = grp.collect {
      case (x, ys) if (ys.lengthCompare(1) > 0) => (x,ys.size)
    }
    println(dupesCnt)
    // Map(5 -> 4, 1 -> 4, 2 -> 4, 7 -> 3, 3 -> 3)


    // approach3

    val UniNDups = lst.foldLeft((Set.empty[Int], Set.empty[Int])) {
      case ((seen, dupes), head) => if (seen(head)) (seen, dupes + head) else (seen + head, dupes)
    }

    println(UniNDups) // (Set(5, 1, 6, 9, 2, 7, 3, 8, 4),Set(5, 1, 2, 7, 3))

    println(uniqNDupes(lst))
    // (List(8, 9, 7, 6, 5, 4, 3, 2, 1),List(1, 3, 5, 7, 2, 7, 5, 3, 2, 5, 2, 1, 1))


    val nums = Set(1,2,3,5,8,9,15,20)

    println(nums(2)) // true
    println(nums(4)) // false
    // on Set to add element use "+"
    println(nums + 6) // Set(5, 20, 1, 6, 9, 2, 3, 8, 15)
    println(nums)  // Set(5, 20, 1, 9, 2, 3, 8, 15)

  }


  def uniqNDupes(lst: List[Int]): Product = {
    def uniqNDupesR(lst: List[Int], uni: List[Int], dupes: List[Int]): Product = lst match{
      case Nil => (uni, dupes)
      case head :: _ if (!uni.contains(head)) => uniqNDupesR(lst.tail, head +: uni, dupes)
      case head :: _ => uniqNDupesR(lst.tail, uni, head +: dupes)
    }
    uniqNDupesR(lst, List[Int](), List[Int]())
  }

}
