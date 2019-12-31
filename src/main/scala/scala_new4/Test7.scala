package scala_new4

class Writer(val firstName: String, val surName: String, val year: Int ) {
  def fullname(): String = firstName + " " + surName
}

class Novel(val name: String, val yearOfRelease: Int, val author: Writer) {
  def authorAge(): Int = yearOfRelease - author.year
  def isWrittenBy(author: Writer): Boolean = author == this.author
  def copy(newyear: Int): Novel = new Novel(name, newyear, author)
}


object Test7 {
  def main(args: Array[String]): Unit = {

    val w1 = new Writer("Chetan", "Bhagath", 1975)
    val w2 = new Writer("Chetan", "Bhagath", 1975)


    val n1 = new Novel("half girlfriend", 2016, w1)

    println(w1.fullname())
    println(n1.authorAge())

    println(n1.isWrittenBy(w1)) // true
    println(n1.isWrittenBy(w2)) // false

    val n1new = n1.copy(2019)

    println(n1new.name)
    println(n1new.yearOfRelease)







  }

}
