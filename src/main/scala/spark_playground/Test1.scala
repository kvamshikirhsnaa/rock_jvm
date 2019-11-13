package spark_playground

import org.apache.spark.sql.SparkSession

object Test1 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master("local").
      appName("sample").getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val empDF = spark.read.option("header", "true").
      option("inferSchema", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\emp.txt")

    val deptDF = spark.read.option("header", "true").
      option("inferSchema", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\dept.txt")

    empDF.show
    deptDF.show

    val empInProj = empDF.join(deptDF, 'id === 'eid, "leftsemi")

    empInProj.show

    val empOnBench = empDF.join(deptDF, 'id === 'eid, "leftanti")

    empOnBench.show

    empDF.createOrReplaceTempView("emp")
    deptDF.createOrReplaceTempView("dept")

    spark.sql("select * from emp e join dept d on e.id = d.eid").show

    spark.sql("select e.id, e.name, e.age, e.sal, e.sex, e.deptno from emp e join dept d on e.id = d.eid").show

    spark.sql("select * from emp where id in (select eid from dept)").show

    // exists is not supporting in spark sql
    //spark.sql("select * from emp where id exists (select eid from dept where emp.id = dept.eid)").show







  }

}
