package scala_new2

object Test13 {
  def main(args: Array[String]): Unit = {

    val dup = List(1,1,1,2,3,4,3,3,5,5,6)

    // group by each element in List, returns Map collection,
    // identity is used to group by each elements in a list
    val grp = dup.groupBy(identity)

    println(grp)
    //Map(5 -> List(5, 5), 1 -> List(1, 1, 1), 6 -> List(6),
    // 2 -> List(2), 3 -> List(3, 3, 3), 4 -> List(4))

    // finding duplicates in List
    val res = grp.collect { case (x, List(_,_,_*)) => (x) }

    // to match in case or map or collect for List
    // if List has many values we need to use "List(_,_,_*)"
    // it will match List with more than 1 value

    println(res)
    //List(5, 1, 3)


    //  finding duplicates in List in another way

    val values = grp.values

    println(values) // returns MapLike collection
    //MapLike(List(5, 5), List(1, 1, 1),
    // List(6), List(2), List(3, 3, 3), List(4))

    val res1 = values.filter(x => x.size > 1)

    println(res1)
    // List(List(5, 5), List(1, 1, 1), List(3, 3, 3))

    // find count of duplicate values

    val cntres = res1.map(x => (x(0), x.size))
    println(cntres)
    //List((5,2), (1,3), (3,3))



  }

}
