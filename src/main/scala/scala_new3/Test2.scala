package scala_new3

object Test2 {
  def main(args: Array[String]): Unit = {

    val word = "hello,world,goodbye,tata"

    val words = split(word, ",")

    words.foreach(x => print(x + " ")) // hello world goodbye tata
    println()
    println(words.size) // 4

  }

  def split(str: String, del: String): Array[String] = {
    def splitR(str: String, arr: Array[String]): Array[String] = str match {
      case str1 if (!str1.contains(del)) => {
        val finalarr = str +: arr
        finalarr.reverse
      }
      case str2 => {
        val head = str2.take(str2.indexOf(del))
        val tail = str2.drop(str2.indexOf(del) + 1)
        splitR(tail, head +: arr)
      }
    }
    splitR(str, Array[String]())
  }

}
