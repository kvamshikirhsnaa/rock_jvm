package scala_new2

object Test5 {
  def main(args: Array[String]): Unit = {

    val nums = List(101,102,103,104,105)
    val names = List("saikrishna", "aravind", "prakash",
      "narahari", "vamshikrishna")


    // zip: used to combine 2 collections based on matched indexes from both collections
    println("ZIP")
    println("----")
    val ziptup = nums.zip(names)
    println(ziptup) // List((101,saikrishna), (102,aravind),
    // (103,prakash), (104,narahari), (105,vamshikrishna))

    val nums2 = List(1,2,3,4,5)
    val names2 = List("a", "b", "c","d")

    val ziptup2 = nums2.zip(names2)
    println(ziptup2)  // List((1,a), (2,b), (3,c), (4,d))
    // here we are missing 5 element form nums2 cuz it doesn't has any match

    println("---------------------")

    // zipAll: used to zip all elements including not matched,
    //          we need to pass default value for not matched pairs
    println("ZIP ALL")
    println("--------")
    val ziptup3 = nums2.zipAll(names2, nums2, " ")
    val ziptup4 = nums2.zipAll(names2, nums2, "z")

    println(ziptup3)
    println(ziptup4)

    println("---------------------")

    val ids = List(1,2,3,4,5)
    val fnames = List("sai", "aravind","prakash","narahari","vamshi")
    val lnames = List("krishna","swami","yangal","yangal","krishna")

    // zipping 3 lists

    println("Without Using ZIPPED")
    println("---------------------")

    val tempzip = ids.zip(fnames)
    val tempzip2 = tempzip.zip(lnames)

    println(tempzip)
    println(tempzip2)

    val finalzip = tempzip2.map(x => (x._1._1, x._1._2, x._2))
    println(finalzip)

    // zipped: for 3 collections we can use zipped
    // instead of writing above like
    // result will be TupleZipped,type we can convert to toList again
    //  to join 4 collections and more we cann't use zipped

    println("ZIPPED")
    println("-------")
    val finalzip2 = (ids,fnames,lnames).zipped // Tuple3Zipped, cuz 3 collections


    //converting to List

    val finallst = finalzip2.toList
    println(finallst)

    println("---------------------")

    println("CUSTOM ZIP FUNCTION")
    println("--------------------")

    val numsnew1 = List(0,1,2,3,4)
    val numsnew2 = List(10,11,12,13)

    println(custZip(numsnew1, numsnew2))

  }

  def custZip(lst: List[Int], xs: List[Int]): List[Tuple2[Int, Int]] = {
    var cnt = 0
    val batch = xs.size
    lst.collect {
      case x if (cnt < batch) => {
        val tup = (x, xs(cnt))
        cnt += 1
        tup
      }
    }
  }

}
