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

    println(m)
    val pairs = m.filter(x => x._2.length > 1)
    println(pairs)

    def findMaxSumEle(lst: List[Int]): Map[Int, List[Tuple2[Int, Int]]] = {
      val len = Range(0, lst.length)
      val maxEles = len.foldLeft(Map[Int, List[Tuple2[Int, Int]]]()) {
        (tempMap, curr) => {
          val len2 = Range(curr + 1, lst.length)
          val lstOfSumEles = len2.foldLeft(tempMap) {
            (tempMap2, cur) => {
              val ele = lst(curr) + lst(cur)
              if (tempMap2.contains(ele)) tempMap2 + (ele -> (tempMap2(ele) ++ List((lst(curr), lst(cur)))))
              else tempMap2 + (ele -> List((lst(curr), lst(cur))))
            }
          }
          lstOfSumEles
        }
      }
      maxEles
    }

    println(findMaxSumEle(lst))

    def findMaxSumEle2(lst: List[Int]): Map[Int, List[Tuple2[Int, Int]]] = {
      var m = Map[Int, List[Tuple2[Int, Int]]]()
      (0 to lst.length).foreach(x => Range(x + 1, lst.length).foreach{ y =>
        val z = lst(x) + lst(y)
        if (m.contains(z)) m += (z -> (m(z) ++ List((lst(x), lst(y)))))
        else m += (z -> List((lst(x), lst(y))))

      })
      m
    }
    println(findMaxSumEle2(lst))



  }

}
