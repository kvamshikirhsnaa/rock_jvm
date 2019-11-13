package scala_new4

// canEqual vs hashCode

import scala.collection.mutable.HashMap

class Employee {

  var id: Int = _

  def this(id: Int){
    this
    this.id = id
  }

}


class Employee2 {

  var id: Int = _

  def this(id: Int){
    this
    this.id = id
  }


  def canEqual(other: Any): Boolean = other.isInstanceOf[Employee2]

  override def equals(other: Any): Boolean = other match {
    case that: Employee2 =>
      (that canEqual this) &&
        id == that.id
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(id)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}


object Test1 {
  def main(args: Array[String]): Unit = {

    val emp1 = new Employee(1)
    val emp2 = new Employee(1)

    val map = new HashMap[Employee, String]()
    map.put(emp1, "saikrishna")
    map.put(emp2, "saikrishna")

    println(map.size)     // 2

    val i1 = 1
    val i2 = 1

    val map2 = new HashMap[Int, String]()

    map2.put(i1, "one")
    map2.put(i2, "one")
    map2.put(i2, "two")

    println(map2.size)     // 1

    // NOTE: here for Wrapper classes(Int, Float, ..) already hashCode and equals method will be implemented
    // so it remove duplicate entries based on hashCode of key and content of value but for Employee class
    // it doesn't have hashCode and equals method implemented so there duplicates are allowed that's why size is 2


    val empnew1 = new Employee2(1)
    val empnew2 = new Employee2(1)
    val empnew3 = new Employee2(2)

    val map3 = new HashMap[Employee2, String]()
    map3.put(empnew1, "saikrishna")
    map3.put(empnew2, "saikrishna")

    println(map3.size)     // 1

    println(empnew1.equals(empnew2))     // true
    println(empnew1.equals(empnew3))     // false
    println(empnew2.equals(empnew3))     // false


  }

}
