package scala_new

case class Emp(name: String, dept: String, desg: String, sal: Double)

//class EmpHike {
//  val hikeSal: PartialFunction[Emp, Emp] = new PartialFunction[Emp, Emp] {
//    override def apply(v1: Emp): Emp = v1 match {
//      case i: Emp if i.dept == "IT" => {
//        if (i.desg == "SSE") Emp( i.name, i.dept, i.desg, i.sal + i.sal * 0.8)
//        else if (i.desg == "TL") Emp( i.name, i.dept, i.desg, i.sal + i.sal * 0.4)
//        else Emp( i.name, i.dept, i.desg, i.sal + i.sal * 0.2 )
//      }
////      case i: Emp if (i.dept == "IT") && (i.desg == "TL") => Emp( i.name, i.dept, i.desg, i.sal + i.sal * 0.4 )
////      case i: Emp if (i.dept == "IT") && (i.desg == "MGR") => Emp( i.name, i.dept, i.desg, i.sal + i.sal * 0.2 )
//      case i: Emp if (i.dept == "management") && (i.desg == "HR") => Emp( i.name, i.dept, i.desg, i.sal + i.sal * 0.5 )
//    }
//
//
//    override def isDefinedAt(x: Emp): Boolean = x match {
//      case i: Emp if i.dept == "IT" => i.desg != "CSE"
//      case k: Emp if k.dept == "management" => k.desg != "MGR"
//
//    }
//  }
//}




  object Test2 {
    def main(args: Array[String]): Unit = {

      println(empl.collect(hikeSal))

    }

    val empl = List(
      Emp("naidu","IT","MGR",900000),
      Emp("eshwar","IT","SSE", 850000),
      Emp("mahesh","management","HR", 500000),
      Emp("nari","management","HR", 650000),
      Emp("krishna","management","MGR", 700000),
      Emp("narahri","management","MGR", 700000),
      Emp("sudheer","IT","TL",800000),
      Emp("anji","IT","SSE", 750000),
      Emp("ramu","management","HR", 600000),
      Emp("venu","IT","CSE", 750000)
    )

    // val emp = new EmpHike

    val hikeSal: PartialFunction[Emp, Emp] = new PartialFunction[Emp, Emp] {
      override def apply(v1: Emp): Emp = v1 match {
        case i: Emp if i.dept == "IT" => {
          if (i.desg == "SSE") Emp( i.name, i.dept, i.desg, i.sal + i.sal * 0.8)
          else if (i.desg == "TL") Emp( i.name, i.dept, i.desg, i.sal + i.sal * 0.4)
          else Emp( i.name, i.dept, i.desg, i.sal + i.sal * 0.2 )
        }
        case i: Emp if (i.dept == "management") && (i.desg == "HR") => Emp( i.name, i.dept, i.desg, i.sal + i.sal * 0.5 )
      }
      override def isDefinedAt(x: Emp): Boolean = x match {
        case i: Emp if i.dept == "IT" => i.desg != "CSE"
        case k: Emp if k.dept == "management" => k.desg != "MGR"
      }
    }


  }












