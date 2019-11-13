package scala_new2

object Test12 {
  def main(args: Array[String]): Unit = {

    val lst = List("female","female","female","male","male","male")

    println(lst)

    def custPair(xs: List[String]): List[(String, Int)] = {
      var cnt1 = 0
      var cnt2 = 0
      val pair = xs.map {
        case i if (i == "male") => {
          val m = (i, cnt1)
          cnt1 += 1
          m
        }
        case j => {
          val f = (j, cnt2)
          cnt2 += 1
          f
        }
      }
      pair
    }

    val pair = custPair(lst)

    println(pair) // List((female,0), (female,1), (female,2), (male,0), (male,1), (male,2))

    val custSort = pair.sortBy(x => (x._2,x._1))

    println(custSort) //List((female,0), (male,0), (female,1), (male,1), (female,2), (male,2))

    val empl = Seq(Emp(101,"prakash",26, 70), Emp(102,"narahri",29, 80), Emp(103,"aravind",25, 90),
      Emp(104,"saikrishna",24, 100), Emp(105,"vamshikrishna", 28, 100), Emp(106,"anji", 35, 70),
      Emp(107,"anji",39, 80), Emp(108,"govind",42, 70))

    println(empl.sortBy(x => x.age))
    //List(Emp(104,saikrishna,24,100), Emp(103,aravind,25,90), Emp(101,prakash,26,70),
    // Emp(105,vamshikrishna,28,100), Emp(102,narahri,29,80), Emp(106,anji,35,70),
    // Emp(107,anji,39,80), Emp(108,govind,42,70)

    println(empl.sortBy(x => x.name))
    // List(Emp(106,anji,35,70), Emp(107,anji,39,80), Emp(103,aravind,25,90), Emp(108,govind,42,70),
    // Emp(102,narahri,29,80), Emp(101,prakash,26,70), Emp(104,saikrishna,24,100), Emp(105,vamshikrishna,28,100))

    val sortBySalAndAge = (e1: Emp, e2: Emp) => {
      if (e1.sal == e2.sal) e1.age > e2.age else e1.sal > e2.sal
    }

    println(empl.sortWith(sortBySalAndAge))
    //List(Emp(105,vamshikrishna,28,100), Emp(104,saikrishna,24,100), Emp(103,aravind,25,90),
    // Emp(107,anji,39,80), Emp(102,narahri,29,80), Emp(108,govind,42,70)
    // , Emp(106,anji,35,70), Emp(101,prakash,26,70))

  }

  case class Emp(id: Int, name: String, age: Int, sal: Int)


}
