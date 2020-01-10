package udemy_rockjvm

abstract class MyList {
  def head: Int
  def tail: MyList
  def isEmpty: Boolean
  def add(x: Int): MyList
  def remove(x: Int): MyList
  def printElements: String
  def contains(x: Int): Boolean
  override def toString: String = "[" + printElements + "]"

}

object Empty extends MyList {
  def head: Int = throw new NoSuchElementException
  def tail: MyList = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add(x: Int): MyList = new Cons(x, this)
  def contains(x: Int): Boolean = false
  def remove(x: Int): MyList = throw new NoSuchElementException
  def printElements: String = ""

}

class Cons(h: Int, t: MyList) extends MyList {
  def head: Int = h
  def tail: MyList = t
  def isEmpty: Boolean = false
  def add(x: Int): MyList = new Cons(x, this)
  def contains(x: Int): Boolean = {
    if (h == x) true
    else if (t.contains(x)) true
    else false

  }
  def remove(x: Int): MyList = {
    if (h == x) t
    else if (t.contains(x)) new Cons(h, t.remove(x))
    else throw new NoSuchElementException
  }
  def printElements: String = {
    if (t.isEmpty) "" + h
    else h + " " + t.printElements
  }
}

object LinkedList {
  def main(args: Array[String]): Unit = {

    val empty = Empty
    // println(empty.head)
    // println(empty.tail)
    println(empty.isEmpty)
    println(empty.add(3))

    val lst = new Cons(4, new Cons(5, new Cons(6, new Cons(7, Empty))))
    println(lst.isEmpty)
    println(lst.head)
    println(lst.tail)
    println(lst.add(3))
    println(lst.add(3).add(2).add(1).add(0))
    println(lst.contains(2))

    val lstnew = lst.add(3).add(2).add(1).add(0)
    println(lstnew.contains(2))
    println(lstnew.contains(0))
    println(lstnew.contains(7))
    println(lstnew.remove(5))
    println(lstnew.remove(0))
    println(lstnew.remove(9))
  }
}
