package scala_new4

import scala.annotation.tailrec

object Test10 extends App {

  def fractionToRecurringDecimal(numerator: Int, denominator: Int): String = {

    def f2d(n: Long, d: Long): String = {

      /*
   recurringDecimal(17,3)
   q = 5
   r = 2

   5.recurringDecimalTailrec(2, 3)
   recurringDecimalTailrec(2 * 10, 3)
   recurringDecimalTailrec(20, 3)

 */

      @tailrec
      def findRecurrence(quot: Long, lstOfQuots: List[Long], rem: Long, lstOfRems: List[Long], currInd: Int): Int = {

        if (lstOfQuots.isEmpty || lstOfRems.isEmpty) -1
        else if (quot == lstOfQuots.head && rem == lstOfRems.head) currInd
        else findRecurrence(quot, lstOfQuots.tail, rem, lstOfRems.tail, currInd + 1)
      }


      @tailrec
      def recurringDecimalTailrec(num: Long, den: Long, lstOfQuots: List[Long], lstOfRems: List[Long]): String = {

        val quot = (num * 10) / den
        val rem = (num * 10) % den

        if (rem == 0) (lstOfQuots :+ quot).mkString("")
        //else recurringDecimalTailrec(newRem, deno, )
        else {
          val recurrenceStartIndex = findRecurrence(quot: Long, lstOfQuots: List[Long], rem: Long,lstOfRems: List[Long], 0)

          if (recurrenceStartIndex == -1) recurringDecimalTailrec(rem, den, lstOfQuots :+ quot, lstOfRems :+ rem )
          else {
            val (beforeRecurrence, recurrence) = lstOfQuots.splitAt(recurrenceStartIndex)
            s"${beforeRecurrence.mkString("")}(${recurrence.mkString("")})"
          }

        }

      }


      if (n > 0 && d < 0) s"-${f2d(n, -d)}"
      else if (n < 0 && d > 0) s"${f2d(-n, d)}"
      else {
        val quotient = n / d
        val reminder = n % d

        if (reminder == 0) s"$quotient"
        else s"$quotient.${recurringDecimalTailrec(reminder, d, List.empty, List.empty)}"
      }

    }
    require(denominator != 0)
    f2d(numerator, denominator)
  }
  println(fractionToRecurringDecimal(1,3))
  println(fractionToRecurringDecimal(4,5))
  println(fractionToRecurringDecimal(1,2003))
  //println(fractionToRecurringDecimal(1,0))




}
