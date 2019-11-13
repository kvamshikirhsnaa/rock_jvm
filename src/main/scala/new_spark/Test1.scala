package new_spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._

import scala.util.Random
import scala.math.BigDecimal

case class Emp(id: Int, name: String, sal: Int, dno: Int)

case class Dept(dno: Int, dname: String, city: String)


object Test1 {
  def main(args: Array[String]): Unit = {


    val spark = SparkSession.builder().
      appName( "sample21" ).
      master( "local" ).
      getOrCreate

    spark.sparkContext.setLogLevel( "ERROR" )

    spark.conf.set( "spark.sql.autoBroadcastJoinThreshold", -1 )

    import spark.implicits._


    val emp = Seq(Emp(1,"saikrishna",1000000, 11),
      Emp(2,"aravind",80000,12),
      Emp(3,"prakash",50000,14),
      Emp(4,"narahari",40000,13),
      Emp(5,"anji", 60000,12),
      Emp(6,"Gnani",80000,14),
      Emp(7,"govind",30000,11),
      Emp(6,"krishna",20000,13),
      Emp(7,"nani",15000,12),
      Emp(8,"kanna",5000,11),
      Emp(9,"praveen",40000,12))

    val dept = Seq(Dept(11,"manager","hyderabad"),
      Dept(12,"admin","kotakonda"),
      Dept(13,"accounts","mahabubnagar"),
      Dept(14,"sales","narayanapet"),
      Dept(15,"marketing","bangalore"))

    def genEmp: Emp = {
      val randNum = if (Random.nextBoolean()) 0 else Random.nextInt(emp.size)
      emp(randNum)
    }


    val df1 = Seq.fill(1000000)(genEmp).toDF
    val df2 = dept.toDF

//    df1.write.option("header", "true").
//      option("inferSchema", "true").
//      csv("C:\\Users\\Kenche.vamshikrishna\\Desktop\\Spark\\emp")
//
//    df2.write.option("header", "true").
//      option("inferSchema", "true").
//      csv("C:\\Users\\Kenche.vamshikrishna\\Desktop\\Spark\\dept")


  }
}