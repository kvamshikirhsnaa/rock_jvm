package scala_new2

object Test4 {
  def main(args: Array[String]): Unit = {

    val lst = List(1,2,3,4,5,6)

    //zipWithIndex: each ele will turn into tuple, gives index values

    val ziplst = lst.zipWithIndex // gives List og Tuples

    println(ziplst) //List((1,0), (2,1), (3,2), (4,3), (5,4), (6,5))

    val oddInd = ziplst.filter(x => (x._2 % 2 == 1))

    println(oddInd) // List((2,1), (4,3), (6,5))

    // removing indexes from tuple getting only values in List

    val lstnew = oddInd.map(x => x._1)
    println(lstnew)


    val names = List("saikrishna","aravind","prakash","vamshikrishna","narahari")

    val zipnames = names.zipWithIndex

    val zipnames2 = zipnames.map {
      case (name, ind) => s"element $ind = $name"
    }

    println(zipnames2)

    zipnames.foreach {
      case (name, ind) => println(s"element $ind = $name")
    }

    // to print on console use foreach, to store in collection use map

    // Stream: using Stream we can do custom index for zip values

    val fnames = List("saikrishna","aravind","prakash","narahari","vamshikrishna")

    val nameszip = fnames.zip(Stream from 10) // List((saikrishna,10), (aravind,11), (prakash,12),
    // (narahari,13), (vamshikrishna,14))

    println(nameszip)

    // unzip: collection(List) of Tuple2(key,value) divides into 2 separate Tuple of collection(lists)

    val lsttup2 = List((1,"saikrishna"),(2,"aravind"),(3,"prakash"),(4,"narahari"),(5,"vamshikrishna"))

    val unzplst = lsttup2.unzip

    println(unzplst)  // (List(1, 2, 3, 4, 5),List(saikrishna, aravind, prakash, narahari, vamshikrishna))

    // unzip3: collection(List) of Tuple3(key,value1,value2) divides into 3 separate Tuple of collection(lists)

    val lsttup3 = List((1,"saikrishna",24),(2,"aravind",25),(3,"prakash",26),(4,"narahari",29),(5,"vamshikrishna",28))

    val unizp3lst = lsttup3.unzip3

    println(unizp3lst) // (List(1, 2, 3, 4, 5),List(saikrishna, aravind, prakash, narahari, vamshikrishna),
    // List(24, 25, 26, 29, 28))



  }

}
