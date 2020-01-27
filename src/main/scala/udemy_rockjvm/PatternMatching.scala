package udemy_rockjvm

import scala.util.Random

object PatternMatching {
  def main(args: Array[String]): Unit = {

    // switch on steroids
    val random = new Random
    val x = random.nextInt( 10 ) // 0 to 10 any value

    val description = x match {
      case 1 => "the ONE"
      case 2 => "double or nothing"
      case 3 => "third time is the charm"
      case _ => "something else" // _ is WILDCARD, can match anything
    }

    println( x )
    println( description )


    // 1. Decompose values
    case class Person(name: String, age: Int)
    val sai = Person( "Saikrishna", 25 )

    val greeting = sai match {
      case Person( n, a ) => s"Hi, i am $n, i am $a years old"
      case _ => "i don't know who i am"
    }

    println( greeting )

    // guarding
    val greeting2 = sai match {
      case Person( n, a ) if a < 18 => s"Hi, i am $n, i cann't cast vote"
      case Person( n, a ) if a > 18 => s"Hi, i am $n, i can cast vote"
      case _ => "i don't know who i am"
    }
    println( greeting2 )

    /*
    1. cases are matched in order
    2. what if no case match, will throw scala.MatchError
    3. type of PatternMatch expression? unified type of all the types in all the cases
    4. PM works really well with case classes
*/

    // PM on sealed hierarchies
    sealed class Animal
    case class Dog(breed: String) extends Animal
    case class Dolphin(greeting: String) extends Animal

    val animal: Animal = Dog( "Terra Nova" )

    animal match {
      case Dog( someBreed ) => println( s"Matched a dog of the $someBreed breed" )
    }

    /*
    when we use sealed keyword, it gives warning if any cases doesn't match in our code
    Warning:(55, 5) match may not be exhaustive.
    It would fail on the following inputs: Animal(), Dolphin(_)
    animal match

    if we remove sealed, it won't give any warning for non match cases
*/

    // match Everything
    val isEven = x match {
      case n if n % 2 == 0 => true
      case _ => false
    } // bad practice

    val isEvenCond = if (x % 2 == 0) true else false // also bad practice
    val isEvenNormal = x % 2 == 0 // good
    println( isEvenNormal )

/*
    Exercise:
    simple function uses PatternMatch
     takes an Expr and returns human readable form

     Sum(Number(2), Number(3)) => 2 + 3
     Prod(Number(2), Number(3)) => 2 * 3
     Sum(Prod(Number(2), Number(1)), Number(3)) => 2 * 1 + 3
     Prod(Sum(Number(2), Number(1)), Number(3)) => (2 + 1) * 3
     Prod(Sum(Number(2), Number(3)), Sum(Number(4), Number(5))) => Sum(Number(2), Number(3)) * Sum(Number(4), Number(5))
                                                                => (2 + 3) * (4 + 5)
*/
    trait Expr
    case class Number(n: Int) extends Expr
    case class Sum(e1: Expr, e2: Expr) extends Expr
    case class Prod(e1: Expr, e2: Expr) extends Expr

    def show(e: Expr): String = e match {
      case Number(n) => s"$n"
      case Sum(e1, e2) => show(e1) + " + " + show(e2)
      case Prod(e1, e2) => {
        def mabeShowParenthesis(exp: Expr): String = exp match {
          case Prod(_, _) => show(exp)
          case Number(_) => show(exp)
          case _ => "(" + show(exp) + ")"
        }
        mabeShowParenthesis(e1) + " * " + mabeShowParenthesis(e2)
      }
    }
    println(show(Sum(Number(2), Number(3))))
    println(show(Prod(Number(2), Number(3))))
    println(show(Sum(Prod(Number(2), Number(1)), Number(3))))

    println(show(Prod(Sum(Number(2), Number(1)), Number(3))))
    /*
    Prod(Prod(Sum(Number(2), Number(1)), Number(3)))
    mabeShowParenthesis(Sum(Number(2), Number(1)) * mabeShowParenthesis(3)
      "(" + show(Sum(Number(2), Number(1)) + ")" * 3
      "(" + 2 + 1 + ")" * 3
      (2 + 1) * 3
     */

    println(show(Prod(Sum(Number(2), Number(3)), Sum(Number(4), Number(5)))))




  }

}
