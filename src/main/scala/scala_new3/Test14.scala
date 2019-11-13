package scala_new3

abstract class IntSet {
  def contains(x: Int): Boolean
  def incl(x: Int): IntSet
}

class Empty extends IntSet {
  def contains(x: Int) = false
  def incl(x: Int) = new NonEmpty(new Empty, x, new Empty)
  override def toString = "."
}

class NonEmpty(left: IntSet, ele: Int, right: IntSet) extends IntSet {
  def contains(x: Int): Boolean = {
    if (x < ele) left contains x
    else if(x > ele) right contains x
    else true
  }

  def incl(x: Int): IntSet = {
    if (x < ele) new NonEmpty(left incl x, ele,  right)
    else if(x > ele) new NonEmpty(left, ele, right incl x)
    else this
  }

  override def toString = s"{$left,$ele,$right}"
}

object Test14 {
  def main(args: Array[String]): Unit = {

    val emp = new Empty
    println(emp.contains(4))   // false
    println(emp.incl(2))       // {.,2,.}

    val t1 = new NonEmpty(new Empty,3, new Empty)

    println(t1)               // {.,3,.}
    println(t1.contains(2))   // false
    println(t1.contains(3))   // true
    println(t1.contains(4))   // false
    println(t1.incl(4))       // {.,3,{.,4,.}}
    println(t1.contains(4))   // false
    println(t1)               // {.,3,.}

    val t2 = t1 incl 4        // returns new IntSet
    println(t2)                    // {.,3,{.,4,.}}
    println(t2.contains(4))        // true

    val t3 = t2 incl 5
    println(t3)            // {.,3,{.,4,{.,5,.}}}

    val t4 = t3 incl 1
    println(t4)           // {{.,1,.},3,{.,4,{.,5,.}}}


  }

}
