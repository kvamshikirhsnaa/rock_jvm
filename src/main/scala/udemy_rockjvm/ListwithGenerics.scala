package udemy_rockjvm

abstract class MyListGen[+A] {
  def head: A
  def tail: MyListGen[A]
  def isEmpty: Boolean
  def add[B >: A](x: B): MyListGen[B]
  def remove[B >: A](x: B): MyListGen[B]
  def printElements: String
  def contains[B >: A](x: B): Boolean
  override def toString: String = "[" + printElements + "]"

}

object EmptyGen extends MyListGen[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyListGen[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](x: B): MyListGen[B] = new ConsGen(x, this)
  def contains[B >: Nothing](x: B): Boolean = false
  def remove[B >: Nothing](x: B): MyListGen[B] = throw new NoSuchElementException
  def printElements: String = ""

}

class ConsGen[+A](h: A, t: MyListGen[A]) extends MyListGen[A] {
  def head: A = h
  def tail: MyListGen[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](x: B): MyListGen[B] = new ConsGen(x, this)
  def contains[B >: A](x: B): Boolean = {
    if (h == x) true
    else if (t.contains(x)) true
    else false

  }
  def remove[B >: A](x: B): MyListGen[B] = {
    if (h == x) t
    else if (t.contains(x)) new ConsGen(h, t.remove(x))
    else throw new NoSuchElementException
  }
  def printElements: String = {
    if (t.isEmpty) "" + h
    else h + " " + t.printElements
  }
}

object ListwithGenerics {
  def main(args: Array[String]): Unit = {

    val empty = EmptyGen
    // println(empty.head)
    // println(empty.tail)
    println(empty.isEmpty)
    println(empty.add(3))

    val lst1 = empty.add(3)

    println(lst1.add("hello"))

    val lst2 = empty.add("hello")

    println(lst2.add(4))


    val lstnew1 = new ConsGen[Int](5, empty)
    println(lstnew1)
    println(lstnew1.add(0, new ConsGen("hello", empty)).add("hi"))
    println(lstnew1.add(0).add("hello").add("hi"))
    println(lstnew1.add(true).add(5.0).add(List(1,2,3), List(4,5,6)))
    println(lstnew1.add(true).add(5.0).add((List(1,2,3), List(4,5,6))))

  }

}

