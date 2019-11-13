package scala_new2

object Test8 {
  def main(args: Array[String]): Unit = {

    val arr = Array(Engineer("Eshwar"), Manager("Sudheer"))
    // it gives
    // arr: Array[Product with Serializable with Employee] =
    //           Array(Engineer("Eshwar"), Manager("Sudheer"))

    // here while creating Array we didn't gave type so
    // it creates type as "Product with Serializable with Employee"
    // which means, the Array contains childs of trait Employee
    // here Employee is trait

    // we can give Array type Employee while creating
    // so it will give Array[Employee] it won't allow others
    // objects which are not childs of Employee

    val arr2 = Array[Employee](Engineer("Mahesh"), Manager("Nari"))
    // arr2: Array[Employee](Engineer("Mahesh"), Manager("Nari"))

    val arr3 = Array[Engineer](Engineer("Mahesh"), Engineer("Nari"))
    // arr3: Array[Engineer](Engineer("Mahesh"), Manager("Nari"))


  }

  trait Employee

  case class Engineer(name: String) extends Employee
  case class Manager(name: String) extends Employee



}
