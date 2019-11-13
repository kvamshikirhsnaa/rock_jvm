package scala_new4

object Test4 {
  def main(args: Array[String]): Unit = {

    val lst = List( 1, 3, 4, 6, 7, 8, 2, 15, 13, 5, 9 )
    println( secondHighestEle( lst ) )
    println( secondHighestEle2( lst ) )

  }

  def secondHighestEle(x: List[Int]): Int = {
    var max = Math.max( x( 0 ), x( 1 ) )
    var secondMax = Math.min( x( 0 ), x( 1 ) )
    for (i <- Range( 2, x.length )) {
      if (x( i ) > max) {
        secondMax = max
        max = x( i )
      }
      else if (x( i ) > secondMax) {
        secondMax = x( i )
      }
    }
    secondMax
  }


  def secondHighestEle2(x: List[Int]) = {
    var secondMax = Integer.MIN_VALUE
    val max = x.foldLeft( 0 ) { (a, b) => {
      if (a > b) {
        if (b > secondMax) {
          secondMax = b
        }
        a
      }
      else {
        if (a > secondMax) {
          secondMax = a
        }
        b
      }
    }}
    (max, secondMax)
  }

}
