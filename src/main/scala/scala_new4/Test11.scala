package scala_new4

import scala.annotation.tailrec

object Test11 extends App {

  def checkValidParenthesis(s: String): Boolean = {
    @tailrec
    def checkValidParensTailrec(s: String, openCnt: Int): Boolean = {
      if (s.isEmpty) openCnt == 0
      else if (s.head == ')' && openCnt == 0) false
      else if (s.head == '(') checkValidParensTailrec(s.tail, openCnt + 1)
      else checkValidParensTailrec(s.tail, openCnt - 1)
    }
    checkValidParensTailrec(s, 0)
  }

  def testCheckValidParens() = {
    println(checkValidParenthesis("("))  // false
    println(checkValidParenthesis(")"))  // false
    println(checkValidParenthesis("((")) // false
    println(checkValidParenthesis("))")) // false
    println(checkValidParenthesis("()"))  // true
    println(checkValidParenthesis("(())"))  // true
    println(checkValidParenthesis("((()))")) // true
    println(checkValidParenthesis("()()()")) // true
  }

  def generateValidParenthesis(n: Int): List[String] = {
    @tailrec
    def genValidParensTailrec(nRemaining: Int, acc: Set[String]): Set[String] = {
      if (nRemaining == 0) acc
      else {
        val newString = for {
          str <- acc
          ind <- 0 until str.length
        } yield {
          val (before, after) = str.splitAt(ind)
          s"$before()$after"
        }
        genValidParensTailrec(nRemaining - 1, newString)
      }
    }
    genValidParensTailrec(n - 1, Set("()")).toList
  }

  def testGenValidParens() = {
    println(generateValidParenthesis(1))
    println(generateValidParenthesis(2))
    println(generateValidParenthesis(3))
    println(generateValidParenthesis(4))
    println(generateValidParenthesis(10))

  }

  //testCheckValidParens()
  testGenValidParens()

}
