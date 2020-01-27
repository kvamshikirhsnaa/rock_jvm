package udemy_rockjvm

abstract class Maybe[+T] {

  def map[B](transform: T => B): Maybe[B]
  def flatMap[B](transform: T => Maybe[B]): Maybe[B]
  def filter(predicate: T => Boolean): Maybe[T]

}

case object MaybeNot extends Maybe[Nothing] {
  def map[B](transform: Nothing => B): Maybe[Nothing] = MaybeNot
  def flatMap[B](transform: Nothing => Maybe[B]): Maybe[B] = MaybeNot
  def filter(predicate: Nothing => Boolean): Maybe[Nothing] = MaybeNot
}

case class Just[+T](value: T) extends Maybe[T] {
  def map[B](transform: T => B): Maybe[B] = Just(transform(value))
  def flatMap[B](transform: T => Maybe[B]): Maybe[B] = transform(value)
  def filter(predicate: T => Boolean): Maybe[T] = {
    if (predicate(value)) this
    else MaybeNot
  }

}

object MaybeTest {
  def main(args: Array[String]): Unit = {

    val jst3 = Just(3)
    println(jst3.map(x => x * 3))
    println(jst3.filter(x => x % 2 == 0))
    println(jst3.filter(x => x % 3 == 0))
    println(jst3.flatMap(x => Just(x + 3)))
    println(jst3.flatMap(x => Just(x % 2 == 0)))
    println(jst3.flatMap(x => Just(x % 3 == 0)))

  }
}
