package scala_new2

object Test7 {
  def main(args: Array[String]): Unit = {

    val arr = new Array[String](3)

    arr.foreach(x => print(x + " ")) // null null null
    println()

    process(arr) // just updates arr , returns unit
    arr.foreach(x => print(x + " ")) // scala spark hive
    println()

    // Creating Empty List in various ways

    val lst = List() // List(), type is List[Nothing]
    val lst1 = Nil // List(), type is Nil.type
    val lst2 = List[Int]() // List(), type is List[Int]

    println(lst)
    println(lst1)
    println(lst2)

    println(lst == lst1) // true
    println(lst == lst2) // true
    println(lst1 == lst1) // true

    // Nil != Nothing, Nil == List[Nothing]

    println(lst.hashCode()) // 473519988
    println(lst1.hashCode()) // 473519988
    println(lst2.hashCode()) // 473519988

    var lstnew = List() // List[Nothing]

    //lstnew = lstnew :+ 1 // cuz lstnew is defined as List[Nothing]
                        // we can't add Int type to Nothing so
                        // it is not valid

    val lstnew1 = lstnew :+ 1 // List[Int]

    println(lstnew1) // List(1)

    // Creating nonEmpty List in various ways

    val nlst = List(1,2,3,4) // List(1,2,3,4)
    val nlst2 = 1 :: 2 :: 3 :: 4 :: Nil  // List(1,2,3,4)
    val nlst3 = 1 :: (2 :: (3 :: (4 :: Nil))) // List(1,2,3,4)

    println(nlst)
    println(nlst2)
    println(nlst3)

    val x = List(1,2.0,3F,4L) // convertes every thing into Double
    // cuz Double is highest data type

    println(x) // x: List[Double] = List(1.0, 2.0, 3.0, 4.0)

    // if we want keep data as it is, do not want to change data type
    // of any data use Number as Type, it is parent of all Numeric datatypes

    // Number is from java.lang
    val y = List[Number](1,2.0,3F,4L)
    println(y)//y: List[Number] =  List(1, 2.0, 3.0, 4)



  }

  def process(xs: Array[String]) = {
    xs(0) = "scala"
    xs(1) = "spark"
    xs(2) = "hive"
  }

}
