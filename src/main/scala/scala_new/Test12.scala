package scala_new

object Test12 {
  def main(args: Array[String]): Unit = {

    val numlst1 = List(1,2,3,4,5)

    // foldLeft:

    println("FOLD LEFT")
    println("----------")
    val res = numlst1.foldLeft(0){
      (acc, num) => println(s"$acc $num")
        (acc + num)}

    println(res)


    val lst2 = List(5,8,1,9,4,3,2,7,10,6)

    // finding max value
    val res1 = lst2.foldLeft(Int.MinValue) {
      (acc, num) => Math.max(acc,num)
    }

    println(res1)

    val res2 = numlst1.foldLeft(1){ (acc, num) => acc * num}

    println(res2)

    // reduceLeft

    println("REDUCE LEFT")
    println("------------")
    val res3 = numlst1.reduceLeft(_ + _)
    println(res3) // 15

    //finding max value

    val res4 = lst2.reduceLeft(Math.max(_,_))
    println(res4)  // 10


    //scanLeft

    println("SCAN LEFT")
    println("----------")
    val product = (x: Int, y: Int) => {
      println(s"$x $y")
      x * y
    }

    val res5 = numlst1.scanLeft(1)(product)

    println(res5) // returns a List including initial value


    // reduceLeft doesn't take initial value, i/p and o/p are same type
    // foldLeft takes initial value returns result as initial value type
    // scanLeft takes initial value, returns as List including initial value






  }

}
