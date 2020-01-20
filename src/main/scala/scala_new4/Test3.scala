package scala_new4

import scala.collection.mutable._

object Test3 {
  def main(args: Array[String]): Unit = {

    val m = Map[Int, List[Tuple2[Int, Int]]]()
    val lst = List(1,4,2,3,5,9)

    for (i <- Range(0, lst.length)){
      for (j <- Range(i+1, lst.length)) {
        val k = lst(i) + lst(j)
        if (m.contains(k)){
          m += (k -> ( m(k) ++ List( (lst(i), lst(j)) ) ))
        }
        else
          m += (k -> List((lst(i), lst(j))))
      }
    }

    val pairs = m.filter(x => (x._2).length > 1)

    println(pairs)




  }

}
