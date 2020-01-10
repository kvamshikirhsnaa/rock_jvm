package udemy_rockjvm

object ListwithGen {
  def main(args: Array[String]): Unit = {

    trait MyTransformer[-A, B] {
      def transform(ele: A): B
    }

    trait MyPredicate[-T] {
      def test(ele: T): Boolean
    }

    abstract class MyList[+A] {
      def head: A
      def tail: MyList[A]
      def isEmpty: Boolean
      def add[B >: A](ele: B): MyList[B]
      def contains[B >: A](ele: B): Boolean
      def remove[B >: A](ele: B): MyList[B]
      def ++[B >: A](list: MyList[B]): MyList[B]
      def map[B](transformer: MyTransformer[A,B]): MyList[B]
      def filter(predicate: MyPredicate[A]): MyList[A]
      def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B]

      def printElements: String
      override def toString: String = "[" + printElements + "]"
    }

    object Empty extends MyList[Nothing] {
      def head: Nothing = throw new NoSuchElementException
      def tail: Nothing = throw new NoSuchElementException
      def isEmpty: Boolean = true
      def add[A >: Nothing](x: A): MyList[A] = new Cons(x, this)
      def contains[A >: Nothing](x: A): Boolean = false
      def remove[A >: Nothing](x : A): MyList[Nothing] = throw new NoSuchElementException
      def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
      def map[B](transformer: MyTransformer[Nothing, B]): MyList[B] = Empty
      def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = Empty
      def flatMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B] = Empty

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
      def map[B](transformer: MyTransformer[A, B]): MyList[B] = {
        new Cons(transformer.transform(h), t.map(transformer))
      }
      def filter(predicate: MyPredicate[A]): MyList[A] = {
        if (predicate.test(h)) new Cons(h, t.filter(predicate))
        else t.filter(predicate)
      }
      def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] = {
        transformer.transform(h) ++ t.flatMap(transformer)
      }
      def printElements: String = {
        if (t.isEmpty) "" + h
        else h + " " + t.printElements
      }

    }

    val empty = Empty
    println(empty)
    println(empty.isEmpty)
    // println(empty.head)
    // println(empty.tail)
    val lst = empty.add(2).add(4).add(5).add(6).add(8).add(9)
    println(lst)
    println(lst.contains(4))
    println(lst.contains(7))
    println(lst.remove(4))
    println(lst.map(new MyTransformer[Int, Int] {
      def transform(x: Int): Int = x * 2
    }))
    println(lst.filter(new MyPredicate[Int] {
      def test(x: Int): Boolean = x % 2 == 0
    }))
    println(lst.flatMap(new MyTransformer[Int, MyList[Int]] {
      def transform(x: Int): MyList[Int] = new Cons(x, new Cons(x + 2, Empty))
    }))


    val lstnew: Cons[Any] = new Cons[Any](1, new Cons[String]("hello", Empty))
    println(lstnew)
    println(lstnew.getClass)
    println(lstnew.contains(3))
    println(lstnew.remove(2))


  }

}
