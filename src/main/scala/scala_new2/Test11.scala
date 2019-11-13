package scala_new2

object Test11 {
  def main(args: Array[String]): Unit = {

    val lst1 = List(1,2,3,4,5,6,7,8,9)

    //mkString: converts collection of elements to String
    // it optionally takes 3 args start value, ele separator
    // end value

    println(lst1.mkString)    // 123456789
    println(lst1.mkString(",")) // 1,2,3,4,5,6,7,8,9
    println(lst1.mkString(":")) // 1:2:3:4:5:6:7:8:9

    //mkString with start and end

    println(lst1.mkString("[", ",", "]")) // [1,2,3,4,5,6,7,8,9]
    println(lst1.mkString("{", ":", "}")) // {1:2:3:4:5:6:7:8:9}


    //fill,takes 2 arguments like currying
    // gives how many times the given result

    println(List.fill(10)(0)) // List(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    println(List.fill(10)(0).mkString) // 0000000000

    val cid = 123
    val cid2 = 12456

    // our aim every id should have 10digits if id size less than 10
    // zeros should prepend to cid

    val res1 = List.fill(10 - cid.toString.size)(0).mkString + cid
    val res2 = List.fill(10 - cid2.toString.size)(0).mkString + cid2

    println(res1) // 0000000123
    println(res2) // 0000012456


    println(List.fill(1,2)(10)) // List(List(10, 10))

    println(List.fill(3,6)(10)) // List(List(10, 10, 10, 10, 10, 10),
    // List(10, 10, 10, 10, 10, 10), List(10, 10, 10, 10, 10, 10))

    // it is like matrix 3 list ele(rows) and 6 columns having 10 as element

    println(List.fill(2,3,4)(10))
    // List(List(List(10, 10, 10, 10), List(10, 10, 10, 10), List(10, 10, 10, 10)), // this is first ele
    //      List(List(10, 10, 10, 10), List(10, 10, 10, 10), List(10, 10, 10, 10))) // this is second ele

    // main list has 2 sub lists, in each sub list again have 3 sublists, these 3 sublists contains 4, 10 values





  }

}
