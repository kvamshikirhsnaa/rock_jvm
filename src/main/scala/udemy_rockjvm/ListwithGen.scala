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
      def size: Int
      def ++[B >: A](list: MyList[B]): MyList[B]
      def printElements: String
      override def toString: String = "[" + printElements + "]"

      // HOFs
      def map[B](transformer: A => B): MyList[B]
      def filter(predicate: A => Boolean): MyList[A]
      def flatMap[B](transformer: A => MyList[B]): MyList[B]
      def foreach(transform: A => Unit): Unit
      def sort(compare: (A, A) => Int): MyList[A]
      def zipWith[B,C](list: MyList[B], zip: (A, B) => C): MyList[C]
      def fold[B](start: B)(transform: (B, A) => B): B

    }

    object MyEmpty extends MyList[Nothing] {
      def head: Nothing = throw new NoSuchElementException
      def tail: Nothing = throw new NoSuchElementException
      def isEmpty: Boolean = true
      def add[A >: Nothing](x: A): MyList[A] = new Cons(x, this)
      def contains[A >: Nothing](x: A): Boolean = false
      def remove[A >: Nothing](x : A): MyList[Nothing] = throw new NoSuchElementException
      def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
      def size: Int = 0
      def printElements: String = ""

      // HOFs
      def map[B](transformer: Nothing => B): MyList[B] = MyEmpty
      def filter(predicate: Nothing => Boolean): MyList[Nothing] = MyEmpty
      def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] = MyEmpty
      def foreach(transform: Nothing => Unit): Unit = ()
      def sort(compare: (Nothing, Nothing) => Int): MyList[Nothing] = MyEmpty
      def zipWith[B,C](list: MyList[B], zip: (Nothing, B) => C): MyList[C] = {
        if (!list.isEmpty) throw new RuntimeException("lists do not have same length")
        else MyEmpty
      }
      def fold[B](start: B)(transform: (B, Nothing) => B): B = start


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
      def size: Int = {
        if (t.isEmpty) 1
        else 1 + t.size
      }
      def printElements: String = {
        if (t.isEmpty) "" + h
        else h + " " + t.printElements
      }

      // HOFs
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

      def foreach(transform: A => Unit): Unit = {
        transform(h)
        t.foreach(transform)
      }
      def sort(compare: (A, A) => Int): MyList[A] = {
        def insert(x: A, sortedList: MyList[A]): MyList[A] = {
          if (sortedList.isEmpty) new Cons(x, MyEmpty)
          else if (compare(x, sortedList.head) <= 0) new Cons(x, sortedList)
          else new Cons(sortedList.head, insert(x, sortedList.tail))
        }
        val sortedTail = t.sort(compare)
        insert(h, sortedTail)
      }
      def zipWith[B,C](list: MyList[B], zip: (A, B) => C): MyList[C] = {
        if (list.isEmpty) throw new RuntimeException("lists do not have same length")
        else new Cons(zip(h, list.head), t.zipWith(list.tail, zip))
      }
      def fold[B](start: B)(transform: (B, A) => B): B = {
        t.fold(transform(start,h))(transform)
      }

    }

    val empty = MyEmpty
    println(empty)
    println(empty.isEmpty)
    // println(empty.head)
    // println(empty.tail)
    val lst = empty.add(2).add(4).add(5).add(6).add(8).add(9)
    println(lst)   // // [9 8 6 5 4 2]
    println(lst.size)  // 6
    println(lst.contains(4))   // true
    println(lst.contains(7))   // false
    println(lst.remove(4))  // [9 8 6 5 2]
    println(lst.map(x => x * 2))  // [18 16 12 10 8 4]
    println(lst.filter(x => x % 2 == 0))   // [8 6 4 2]
    println(lst.flatMap(x => new Cons(x, new Cons(x + 2, MyEmpty))))  // [9 11 8 10 6 8 5 7 4 6 2 4]

    val lstnew: Cons[Any] = new Cons[Any](1, new Cons[String]("hello", MyEmpty))
    println(lstnew)  // [1 hello]
    println(lstnew.getClass)   // class udemy_rockjvm.ListwithGen$Cons$1
    //println(lstnew.contains(3))
    println(lstnew.remove(1))    // [hello]
    println(lstnew.size)  // 2

    val lstnew1 = new Cons[Int](5, empty)
    println(lstnew1)   // [5]
    println(lstnew1.add(0, new Cons("hello", empty)).add("hi"))  // [hi (0,[hello]) 5]
    println(lstnew1.add(0).add("hello").add("hi"))  // [hi hello 0 5]
    println(lstnew1.add(true).add(5.0).add(List(1,2,3), List(4,5,6)))  // [(List(1, 2, 3),List(4, 5, 6)) 5.0 true 5]
    println(lstnew1.add(true).add(5.0).add((List(1,2,3), List(4,5,6))))  // [(List(1, 2, 3),List(4, 5, 6)) 5.0 true 5]

    val lst2 = new Cons[Int](4, new Cons(5, new Cons(6, new Cons(7, new Cons(8, MyEmpty)))))
    println(lst2.size)  // 5

    val lstnew2: Cons[Any] = new Cons[Any](1, new Cons[String]("hello", new Cons[String]("world", MyEmpty)))
    //    val lstnew3: Cons[Any] = new Cons[Any](1, new Cons[String]("hello", new Cons[Int](5, MyEmpty)))
    //    val lstnew4: Cons[Any] = new Cons[Any](1, new Cons[Int](2, new Cons[String]("hello", MyEmpty)))

    val lst3 = lst2.add(0).add(1).add(2).add(3)
    println(lst3.size)   // 9
    println(lst3.foreach(x => print(x + " "))) // 3 2 1 0 4 5 6 7 8 ()
    println()
    println(lst3.sort((a, b) => a - b))   // [0 1 2 3 4 5 6 7 8]
    println(lst3.sort((a,b) => b - a))   // [8 7 6 5 4 3 2 1 0]

    val lstOfInts = new Cons(4, new Cons(3, new Cons(2, new Cons(1, MyEmpty))))
    val lstOfStrs = new Cons("Narahari", new Cons("Prakash", new Cons("Aravind", new Cons("Saikrishna", MyEmpty))))

    println(lstOfInts.zipWith[String, String](lstOfStrs, (a, b) => a + "_" + b ))
    println(lstOfInts.zipWith[String, (Int,String)](lstOfStrs, (a, b) => (a,b) ))

    println(lstOfInts.fold(0){(temp, curr) => temp + curr})  // 10
    println(lstOfInts.fold(List[String]()){(temp, curr) => temp :+ curr.toString})  // List(4, 3, 2, 1)

    for {
      n <- lstOfInts
    } println(n)

    val forComprehenision = for {
              n <- lstOfInts
              s <- lstOfStrs
             } yield n + "_" + s
    println(forComprehenision)



  }

}
