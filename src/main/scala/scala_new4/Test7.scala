package scala_new4

import scala.annotation.tailrec

object Test7 {
  def main(args: Array[String]): Unit = {

    val str = "ababcadbabea"
    val str2 = "nfewkjrferhfjkdshferuwhgrsvfbskjfesygelawpqeiufenmzcbzfewwdamcaawfanvdefhefwe"
    val str3 = "aaaabbbbbccccc"

    println( firstNonRepeativeChar( str ) )  // c
    println(firstNonRepeativeChar(str2))   // y
    println(firstNonRepeativeChar(str3))  // _

  }


  def firstNonRepeativeChar(s: String): Char = {
    @tailrec
    def nonRepeativeCharTailrec(ind: Int, m: Map[Char, Int]): Char = {
      if (ind >= s.length) '_'
      else if (m(s(ind)) == 1) s(ind)
      else nonRepeativeCharTailrec(ind + 1, m)
    }
    val aMap = Map[Char, Int]()
    val len = Range( 0, s.length )
    val tempMap = len.foldLeft( aMap ) {
      (tempx, curr) => {
        if (!tempx.contains(s(curr))) tempx + (s(curr) -> 1)
        else tempx + (s(curr) -> (tempx(s(curr)) + 1))
      }
    }
    nonRepeativeCharTailrec(0, tempMap)
  }

}


/*


  using for-comprehension

  val m = Map[Char, Int]()  // mutable map

  for {
    i <- Range( 0, str.length )
  } {
    if (!m.contains( str( i ) )) m += (str( i ) -> 1)

    else  m += (str( i ) -> (m(str(i)) + 1))
  }

  println(m)



*/
