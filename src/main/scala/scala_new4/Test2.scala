package scala_new4

// hashCode vs equals

class Emp(val id: Int, val name: String)


class EmpNew(val id: Int, val name: String) {
  override def hashCode(): Int = {
    val state = Seq(id)
    state.map(x => x.hashCode()).foldLeft(0)((a,b) => 31 * a + b)
  }
  def canEqual(other: Any) = other.isInstanceOf[EmpNew]
  override def equals(other: Any): Boolean = other match {
    case that: EmpNew => that.canEqual(this) && id == that.id && name == that.name
    case _ => false
  }
}


object Test2 {
  def main(args: Array[String]): Unit = {

    val emp1 = new Emp(101, "saikrishna")
    val emp2 = new Emp(101, "saikrishna")
    val emp3 = new Emp(102, "aravind")
    val emp4 = new Emp(102, "prakash")

    val map1 = Map((emp1,"hyderabad"), (emp2,"delhi"), (emp3, "mbnr"), (emp4,"kkd"))
    println(map1.size)  // 4
    println(map1)
    //  Map(scala_new4.Emp@6d7b4f4c -> hyderabad, scala_new4.Emp@527740a2 -> delhi, scala_new4.Emp@13a5fe33 -> mbnr,
    //  scala_new4.Emp@3108bc -> kkd)


    val empnew1 = new EmpNew(101, "saikrishna")
    val empnew2 = new EmpNew(101, "saikrishna")
    val empnew3 = new EmpNew(102, "aravind")
    val empnew4 = new EmpNew(102, "prakash")

    val map2 = Map((empnew1,"hyderabad"), (empnew2,"delhi"), (empnew3, "mbnr"), (empnew4, "kkd"))
    println(map2.size)    // 3
    println(map2)
    //  Map(scala_new4.EmpNew@65 -> delhi, scala_new4.EmpNew@66 -> mbnr, scala_new4.EmpNew@66 -> kkd)
  }

}
