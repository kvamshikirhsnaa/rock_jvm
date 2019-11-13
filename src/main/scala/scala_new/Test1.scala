package scala_new

object Test1 {
  def main(args: Array[String]): Unit = {

    def inlineMeAgain[T](f: => T): T = {
      f
    }
    def inlineme(f: => Int): Int = {
      try {
        inlineMeAgain(return f)
      } catch {
      case ex: Throwable => 5
      }
    }




    def doStuff {
      val res = inlineme(10)
      println("we got: " + res + ". should be 10")
    }

    doStuff

  // without catch it will execute perfect

    def inlineme2(f: => Int): Int = {
      try {
        inlineMeAgain( return f )
      }
    }

   def doStuff2 {
      val res = inlineme2(10)
     println("we got: " + res + ". should be 10")
   }

   doStuff2



    }

}



