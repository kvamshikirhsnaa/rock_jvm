package scala_new3

class Emp(val id: Int, val name: String, val age: Int, val sal: Int) extends Ordered[Emp] {
  override def toString = s"Emp($id, $name, $age, $sal)"
  def compare(that: Emp): Int = {
    if (this.sal == that.sal) this.age - that.age
    else that.sal - this.sal
  }
}

case class EmpNew(id: Int, name: String, age: Int, sal: Int) extends Ordered[EmpNew] {
  def compare(that: EmpNew): Int = {
    if (this.sal == that.sal) this.age - that.age
    else that.sal - this.sal
  }
}

object Test3 {
  def main(args: Array[String]): Unit = {

    val e1 = new Emp(101, "aravind", 25,80000)
    val e2 = new Emp(102, "prakash", 26,50000)
    val e3 = new Emp(103, "saikrishna", 24, 100000)
    val e4 = new Emp(104, "anji", 35, 50000)
    val e5 = new Emp(105, "narahari", 29, 50000)
    val e6 = new Emp(106, "govind", 40, 40000)
    val e7 = new Emp(107, "anji", 38, 80000)

    val empl = Seq(e1,e2,e3,e4,e5,e6,e7)

    println(empl.sorted)
    // List(Emp(103, saikrishna, 24, 100000), Emp(101, aravind, 25, 80000),
    // Emp(107, anji, 38, 80000), Emp(102, prakash, 26, 50000), Emp(105, narahari, 29, 50000),
    // Emp(104, anji, 35, 50000), Emp(106, govind, 40, 40000))


    val emp1 =  EmpNew(101, "aravind", 25,90000)
    val emp2 =  EmpNew(102, "prakash", 26,50000)
    val emp3 =  EmpNew(103, "saikrishna", 24, 120000)
    val emp4 =  EmpNew(104, "anji", 35, 70000)
    val emp5 =  EmpNew(105, "narahari", 29, 70000)
    val emp6 =  EmpNew(106, "govind", 40, 50000)
    val emp7 =  EmpNew(107, "anji", 38, 80000)


    val empls = Seq(emp1, emp2, emp3, emp4, emp5, emp6, emp7)

    println(empls.sorted)
    // List(EmpNew(103,saikrishna,24,120000), EmpNew(101,aravind,25,90000), EmpNew(107,anji,38,80000),
    // EmpNew(105,narahari,29,70000), EmpNew(104,anji,35,70000), EmpNew(102,prakash,26,50000),
    // EmpNew(106,govind,40,50000))



  }

}
