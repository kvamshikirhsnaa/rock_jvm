package cache_scala

class Department(var deptId: Integer, var deptName: String, var loc: String) {
  override def toString: String = "Department[deptId=" + deptId + ", deptName=" + deptName + ", loc=" + loc + "]"
}

