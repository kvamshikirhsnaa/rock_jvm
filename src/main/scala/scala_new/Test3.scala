package scala_new


case class EmpNew(name: String, dept: String, desg: String, sal: Double)


object Test3 {
  def main(args: Array[String]): Unit = {

    println(empl.collect(hikeSalnew()))

    // println(empl.map(hikeSalnew()))


  }

  val empl = List(
    EmpNew( "naidu", "IT", "MGR", 900000 ),
    EmpNew( "eshwar", "IT", "SSE", 850000 ),
    EmpNew( "mahesh", "management", "HR", 500000 ),
    EmpNew( "nari", "management", "HR", 650000 ),
    EmpNew( "krishna", "management", "MGR", 700000 ),
    EmpNew( "narahri", "management", "MGR", 700000 ),
    EmpNew( "sudheer", "IT", "TL", 800000 ),
    EmpNew( "anji", "IT", "SSE", 750000 ),
    EmpNew( "ramu", "management", "HR", 600000 ),
    EmpNew( "venu", "IT", "CSE", 750000 )
  )


  def hikeSalnew(): PartialFunction[EmpNew, EmpNew] = {

    case i: EmpNew if (i.dept == "IT") && (i.desg == "SSE") => EmpNew( i.name, i.dept, i.desg, i.sal + i.sal * 0.8 )

    case i: EmpNew if (i.dept == "IT") && (i.desg == "TL") => EmpNew( i.name, i.dept, i.desg, i.sal + i.sal * 0.4 )

    case i: EmpNew if (i.dept == "IT") && (i.desg == "MGR") => EmpNew( i.name, i.dept, i.desg, i.sal + i.sal * 0.2 )

    case i: EmpNew if (i.dept == "management") && (i.desg == "HR") => EmpNew( i.name, i.dept, i.desg, i.sal + i.sal * 0.5 )

  }
}
