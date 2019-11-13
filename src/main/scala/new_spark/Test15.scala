package new_spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

case class Student(name: String, maths: Double, physics: Double, chemistry: Double, english: Double)


object Test15 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName( "sample21" ).
      master( "local" ).
      getOrCreate

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._

    val s1 = Student("Saikrishna", 100, 95, 90, 90)
    val s2 = Student("Aravind", 90, 90, 85, 85)
    val s3 = Student("Prakash", 85, 80, 75, 70)
    val s4 = Student("Narahari", 80, 75, 70, 70)
    val s5 = Student("Vamshikrishna", 80, 70, 65, 70)

    val df = spark.sparkContext.parallelize(Seq(s1,s2,s3,s4,s5)).toDF
    df.show

    val df2 = df.select($"name", expr("Stack(4, 'maths', maths, 'physics', physics, 'chemistry', chemistry," +
      " 'english', english) as (subject, marks)"))
    df2.show

    val df3 = df2.groupBy('name).agg(sum('marks) as "total", avg('marks) as "avg")

    df3.show

    val cols = df.columns.tail


  }

}
