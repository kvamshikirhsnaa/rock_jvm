package scala_new

object Test7 {
  def main(args: Array[String]): Unit = {

    val file = scala.io.Source.fromFile("C:\\Users\\Kenche.vamshikrishna\\Desktop\\dummy.txt").getLines.toList

    val maxLnCnt = file.map(x => x.size).max.toString.size

    println(maxLnCnt)

    val lines = file.map{x =>
      val lnCnt =  x.size.toString.size
      val cntDiff = maxLnCnt - lnCnt
      val space = " " * cntDiff
      s"${space}${x.size} | $x"
    }

    lines.foreach(println)



  }

}
