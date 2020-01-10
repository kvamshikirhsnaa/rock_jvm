package udemy_rockjvm

object ListExpand {
  def main(args: Array[String]): Unit = {

    abstract class MyListGen[+A] {
      def head: A
      def tail: MyListGen[A]
      def isEmpty: Boolean
      def add[B >: A](x: B): MyListGen[B]
      def remove[B >: A](x: B): MyListGen[B]
      def printElements: String
      def contains[B >: A](x: B): Boolean
      override def toString: String = "[" + printElements + "]"

      def ++[B >: A](anotherList: MyListGen[B]): MyListGen[B]

      def map[B](transformer: MyTransformer[A,B]): MyListGen[B]
      def filter(predicate: MyPredicate[A]): MyListGen[A]
      def flatMap[B](transformer: MyTransformer[A, MyListGen[B]]): MyListGen[B]



    }

    trait MyPredicate[-T] {
      def test(ele: T): Boolean
    }

    trait MyTransformer[-A, B] {
      def transform(ele: A): B
    }

    object EmptyGen extends MyListGen[Nothing] {
      def head: Nothing = throw new NoSuchElementException
      def tail: MyListGen[Nothing] = throw new NoSuchElementException
      def isEmpty: Boolean = true
      def add[B >: Nothing](x: B): MyListGen[B] = new ConsGen(x, this)
      def contains[B >: Nothing](x: B): Boolean = false
      def remove[B >: Nothing](x: B): MyListGen[B] = throw new NoSuchElementException
      def printElements: String = ""

      def ++[B >: Nothing](anotherList: MyListGen[B]): MyListGen[B] = anotherList

      def map[B](transformer: MyTransformer[Nothing,B]): MyListGen[B] = EmptyGen
      def filter(predicate: MyPredicate[Nothing]): MyListGen[Nothing] = EmptyGen
      def flatMap[B](transformer: MyTransformer[Nothing, MyListGen[B]]): MyListGen[B] = EmptyGen

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

      def ++[B >: A](anotherList: MyListGen[B]): MyListGen[B] = {
        new ConsGen(h, t ++ anotherList)
      }

      def filter(predicate: MyPredicate[A]): MyListGen[A] = {
        if (predicate.test(h)) new ConsGen(h, t.filter(predicate))
        else t.filter(predicate)
      }

      def map[B](transformer: MyTransformer[A,B]): MyListGen[B] = {
        new ConsGen(transformer.transform(h), t.map(transformer))
      }

      def flatMap[B](transformer: MyTransformer[A, MyListGen[B]]): MyListGen[B] = {
        transformer.transform(h) ++ t.flatMap(transformer)
      }
    }

    val listOfIntegers: MyListGen[Int] = new ConsGen(1, new ConsGen(2, new ConsGen(3, EmptyGen)))
    val listOfStrings: MyListGen[String] = new ConsGen("hello", new ConsGen("world", EmptyGen))

    println(listOfIntegers)
    println(listOfStrings)

    println(listOfIntegers.map(new MyTransformer[Int, Int] {
      def transform(ele: Int) = ele * 2
    }))

    println(listOfIntegers.filter(new MyPredicate[Int] {
      def test(ele: Int) = ele % 2 == 0
    }))

    println(listOfIntegers.flatMap(new MyTransformer[Int, MyListGen[Int]] {
      def transform(ele: Int): MyListGen[Int] = new ConsGen(ele, new ConsGen(ele + 2, new ConsGen(ele + 4, EmptyGen)))
    }))


  }

}
