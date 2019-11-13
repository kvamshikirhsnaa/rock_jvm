package cache_scala

class Employee(var employeeId: Integer, var employeeName: String, var salary: Double, var email: String) {
  def getEmployeeId: Integer = employeeId

  def getEmployeeName: String = employeeName

  def getSalary: Double = salary

  def getEmail: String = email

  override def hashCode: Int = {
    val prime = 31
    var result = 1
    result = prime * result + (if (employeeId == null) 0
    else employeeId.hashCode)
    result = prime * result + (if (employeeName == null) 0
    else employeeName.hashCode)
    result = prime * result + (if (salary == null) 0
    else salary.hashCode)
    result = prime * result + (if (email == null) 0
    else email.hashCode)
    result
  }

  override def equals(obj: Any): Boolean = {
    if (this == obj) return true
    if (obj == null) return false
    if (getClass ne obj.getClass) return false
    val other = obj.asInstanceOf[Employee]
    if (employeeId == null) if (other.employeeId != null) return false
    else if (!(employeeId == other.employeeId)) return false
    if (employeeName == null) if (other.employeeName != null) return false
    else if (!(employeeName == other.employeeName)) return false
    if (salary == null) if (other.salary != null) return false
    else if (!(salary == other.salary)) return false
    if (email == null) if (other.email != null) return false
    else if (!(email == other.email)) return false
    true
  }

  override def toString: String = "Employee[employeeId=" + employeeId + ", employeeName=" + employeeName + ",salary=" + salary + ",email=" + email + "]"
}