package scala_new4

class Person(val name: String, val favMovie: String) {
  def likes(movie: String) = movie == favMovie

  def hangingOutWith(person: Person): String = s"${this.name} hanging out with ${person.name}"
}

object Test6 {
  def main(args: Array[String]): Unit = {

    val p1 = new Person("Vamshikrishna", "Aparichitudu")
    val p2 = new Person("Vamshikrishna", "96")

    val p3 = new Person("Mahesh", "Dhammu")

    println(p1.likes("Aparichitudu"))
    println(p2.likes("Aparichitudu"))
    println(p1.hangingOutWith(p3))



  }

}
