package scala_new


case class Complex
(
  id: Long,
  name: String,
  nested: Seq[Complex]
)

object Test11 {
  def main(args: Array[String]): Unit = {

    val stuff =
      List(
        Complex(1, "name1",
          List(
            Complex(2, "name2", List()),
            Complex(3, "name3",
              List(
                Complex(4, "name4", List())
              )
            )
          )
        ),
        Complex(5,"name5", List(Complex(6, "name6", List()))
      )
      )

    def flatten(obj: Complex): Seq[Complex] = {
      val unnested = obj.copy(nested = List())
      println(unnested)
      Seq(unnested) ++ obj.nested.flatMap(flatten)
    }

    println(stuff.flatMap(flatten))

  }




}
