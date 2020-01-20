package udemy_rockjvm

object ListwithGen {
  def main(args: Array[String]): Unit = {

    abstract class MyList[+A] {
      def head: A
      def tail: MyList[A]
      def isEmpty: Boolean
      def add[B >: A](ele: B): MyList[B]
      def contains[B >: A](ele: B): Boolean
      def remove[B >: A](ele: B): MyList[B]
      def size(x: Int): Int
      def ++[B >: A](list: MyList[B]): MyList[B]
      def map[B](transformer: A => B): MyList[B]
      def filter(predicate: A => Boolean): MyList[A]
      def flatMap[B](transformer: A => MyList[B]): MyList[B]

      def printElements: String
      override def toString: String = "[" + printElements + "]"
    }

    object MyEmpty extends MyList[Nothing] {
      def head: Nothing = throw new NoSuchElementException
      def tail: Nothing = throw new NoSuchElementException
      def isEmpty: Boolean = true
      def add[A >: Nothing](x: A): MyList[A] = new Cons(x, this)
      def contains[A >: Nothing](x: A): Boolean = false
      def remove[A >: Nothing](x : A): MyList[Nothing] = throw new NoSuchElementException
      def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
      def size(x: Int = 0): Int = x
      def map[B](transformer: Nothing => B): MyList[B] = MyEmpty
      def filter(predicate: Nothing => Boolean): MyList[Nothing] = MyEmpty
      def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] = MyEmpty

      def printElements: String = ""
    }

    class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
      def head: A = h
      def tail: MyList[A] = t
      def isEmpty: Boolean = false
      def add[B >: A](x: B): MyList[B] = new Cons(x, this)
      def contains[B >: A](x: B): Boolean = {
        if (h == x) true
        else t.contains(x)
      }
      def remove[B >: A](x: B): MyList[B] = {
        if (h == x) t
        else new Cons(h, t.remove(x))
      }

      def ++[B >: A](list: MyList[B]): MyList[B] = {
        new Cons(h, t ++ list)
      }
      def size(x: Int = 1): Int = {
        if (t.isEmpty) x
        else t.size(x + 1)
      }
      def map[B](transformer: A => B): MyList[B] = {
        new Cons(transformer(h), t.map(transformer))
      }
      def filter(predicate: A => Boolean): MyList[A] = {
        if (predicate(h)) new Cons(h, t.filter(predicate))
        else t.filter(predicate)
      }
      def flatMap[B](transformer: A => MyList[B]): MyList[B] = {
        transformer(h) ++ t.flatMap(transformer)
      }
      def printElements: String = {
        if (t.isEmpty) "" + h
        else h + " " + t.printElements
      }

    }

    val empty = MyEmpty
    println(empty)
    println(empty.isEmpty)
    // println(empty.head)
    // println(empty.tail)
    val lst = empty.add(2).add(4).add(5).add(6).add(8).add(9)
    println(lst)
    println(lst.size(0))
    println(lst.contains(4))
    println(lst.contains(7))
    println(lst.remove(4))
    println(lst.map(x => x * 2))
    println(lst.filter(x => x % 2 == 0))
    println(lst.flatMap(x => new Cons(x, new Cons(x + 2, MyEmpty))))

    val lstnew: Cons[Any] = new Cons[Any](1, new Cons[String]("hello", MyEmpty))
    println(lstnew)
    println(lstnew.getClass)
    //println(lstnew.contains(3))
    println(lstnew.remove(1))
    println(lstnew.size())

    val lst2 = new Cons[Int](4, new Cons(5, new Cons(6, new Cons(7, new Cons(8, MyEmpty)))))
    println(lst2.size())

    val lst3 = lst2.add(0).add(1).add(2).add(3)
    println(lst3.size(0))

    val lstnew2: Cons[Any] = new Cons[Any](1, new Cons[String]("hello", new Cons[String]("world", MyEmpty)))
    //    val lstnew3: Cons[Any] = new Cons[Any](1, new Cons[String]("hello", new Cons[Int](5, MyEmpty)))
    //    val lstnew4: Cons[Any] = new Cons[Any](1, new Cons[Int](2, new Cons[String]("hello", MyEmpty)))

  }

}
