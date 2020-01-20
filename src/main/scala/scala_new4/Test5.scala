package scala_new4

object Test5 {
  def main(args: Array[String]): Unit = {

    val x = "helllowaababbabbbbbbbbaabhe"
    println(findMaxSubPalindrom(x))
    println(findMaxSubPalindrom2(x))


  }

  def findMaxSubPalindrom(x: String) = {
    var arr = Array[String]()
    for (i <- Range(0, x.length)) {
      for (j <- Range(i + 1, x.length)) {
        val y = x.substring(i, j + 1)
        if (y == y.reverse) {
          arr = arr :+ y
        }
      }
    }
    arr.maxBy(x => x.size)
  }

  def findMaxSubPalindrom2(x: String): String = {
    val len = Range(0,x.length)
    val palindroms = len.foldLeft(Array[String]()) {
      (tempx, curr) => {
        val len2 = Range(curr + 1, x.length)
        val arrOfPalindroms = len2.foldLeft(tempx) {
          (tempx2, cur) => {
            val y = x.substring(curr, cur + 1)
            if (y == y.reverse) {
             tempx2 :+ y
            } else tempx2
          }
        }
        arrOfPalindroms
      }
    }
    palindroms.maxBy(x => x.size)
  }


}
