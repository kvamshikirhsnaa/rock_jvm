package spark_palyground_new

object SplitSubStr {
  def main(args: Array[String]): Unit = {

    val a = "hello,world,bye,tata"

    println(splitSubstr(a, ",").toList)


  }

  def splitSubstr(x: String, del: String) = {
    def splStr(x: String, arr: Array[String]): Array[String] = {
      if (x.contains(del)) {
        val substr = x.substring(0, x.indexOf(del))
        val remstr = x.drop(x.indexOf(del) + 1)
        splStr(remstr, arr :+ substr)
      }
      else arr :+ x
    }
    splStr(x, Array[String]())
  }
}
