package udemy_rockjvm

object AllThePatterns {
  def main(args: Array[String]): Unit = {

    // 1. Constants
    val x: Any = "Scala"
    val constants = x match {
      case 1 => "a number"
      case "Scala" => "hello, i am Scala"
      case true => "The Truth"
      case AllThePatterns => "A singleton object"
    }
    println(constants)   // hello, i am Scala

    // 2. match anything
    // 2.1 WILDCARD
    val matchAnything = x match {
      case _ => "this will return"
      case a => "this will never return"
    }
    println(matchAnything)   // this will return

    // 2.2 variable, we can use variable in side expressions
    val matchAnythinNew = x match {
      case a => s"i have found $a"  // this going to return everytime
      case b => "never return"
    }
    println(matchAnythinNew)   // i have found Scala

    // 3. Tuples
    val aTuple = (2,3)
    val matchTuple = aTuple match {
      case (a, b) => (a * 2, b * 3)
      case _ => (null, null)
    }
    println(matchTuple)  // (4,9)

    val aTuple2 = (2,4)
    val matchTupleNew = aTuple2 match {
      case (1, 1) => s"hello"
      case (something, 4) => s"i have got $something"
    }
    println(matchAnythinNew)   // i have found Scala

    val nestedTuple = (1, (2,3))
    // PMs can be nested on tuples
    val matchNestedTuple = nestedTuple match {
      case (x, (2, something)) => (x, (2, something * 4))
    }
    println(matchNestedTuple)  // (1,(2,12))

    // 4. case classes, constructor pattern
    // PMs can be nested with case classes and Lists as well
    val aList = List(1,2)
    val matchList = aList match {
      case x:Tuple2[String, Int] => "it is tuple"
     // case x:List[Nothing] => "empty list" // it will return cuz aList is type of List, it won't check List type
      // case h :: t :: t2 :: Nil => s"list has elements $h,$t"  // scala.MatchError
      case h :: t :: Nil => s"list has elements $h,$t"
    }
    println(matchList) // list has elements 1,2


    // 5. list patterns
    val standardList = List(1,2,3,6)
    val standardLstMatching = standardList match {
      case List(1, _ , _, _) => // extractor - advance concept
      case List(1, _*) => // list of arbitrary length - advance
      case 1 :: List(_) => //infix pattern
      case List(1,2,3) :+ 6 => // infix pattern
    }


    // 6. type specifiers
    val unknown: Any = 2
    val unknownMatch = unknown match {
      case a: Int => "it is Int"
      case b: String => "it is String"
      case c: Boolean => "it is bool"
      case _ => "some other type"
    }
    println(unknownMatch) // it is Int


    // 6.1
    val aLst = List("hi", "hello")
    val lstMatch = aLst match {
      case a: List[Nothing] => "empty list"
      case b: List[Int] => "list with ints"
      case c: List[String] => "list with strings"
      case d => "list with unknown type"
    }
    println(lstMatch)  // empty list, cuz it will check only 1st level which is type List, not List type Int or String

    // 7. name binding
    val nameBindingMatch = standardList match {
      case nonEmptyLst @ List(1, _*) => nonEmptyLst.size
      case rest @ List(1,_,_) => rest.size
    }
    println(nameBindingMatch)  // 4

    // 8. multi patterns
    val multiPattern = 2
    val multiPatternMatch = multiPattern match {
      case  1 | 2 => "the value is one or two"
      case _ => "something else"
    }
    println(multiPatternMatch) // the value is one or two

    // 9. if guards
    val aNumber = 6
    val guardingMatch = aNumber match {
      case x if x > 10 => s"$x is greater than 10"
      case x if x > 5 => s"$x is greater than 5"
      case x => s"$x is less than 5"
    }
    println(guardingMatch) // 6 is greater than 5


    // Exercise:

    val lst = List(1,2,3)
    val lstMatchNew = lst match {
      case lstOfStrns: List[String] => "list of strings"
      case lstOfInts: List[Int] => "list of ints"
      case _ => "list of unknowns"
    }
    println(lstMatchNew)  // list of strings
    // JVMs trick question


    // generators also based on pattern matching
    val lst2 = List(1,2,3,4)
    val evenOnes = for {
      x <- lst2 if x % 2 == 0
    } yield x
    println(evenOnes) // List(2,4)

    val tuples = List((1,2), (3,4))
    val tuplesNew = for {
      (first, second) <- tuples
    } yield first * second

    println(tuplesNew) // List(2, 12)

    // multiple values defination based on pattern matching
    val tuple = (1,2,3)
    val (a,b,c) = tuple
    println(a) // 1
    println(b) // 2
    println(c) // 3

    val head :: tail = List(1,2,3)
    println(head)  // 1
    println(tail)  // List(2, 3)

    // Partial functions based on pattern matching
    val lst3 = List(1,2,3,4)
    val mappedLst = lst3.map {
      case x if x % 2 == 0 => x + " is Even"
      case 1 => "the ONE"
      case _ => "Something else"
    }
    println(mappedLst) // List(the ONE, 2 is Even, Something else, 4 is Even)


    /* the above code equivalent to
    val mappedLst = lst3.map {x => x match {
      case x if x % 2 == 0 => x + "is Even"
      case 1 => "the ONE"
      case _ => "Something else"
     }
    }

     */









  }

}
