package new_spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._

object Test3 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().
      appName( "sample21" ).
      master( "local" ).
      getOrCreate

    spark.sparkContext.setLogLevel( "ERROR" )

    // spark.conf.set( "spark.sql.autoBroadcastJoinThreshold", -1 )

    import spark.implicits._

    val df1 = spark.read.option( "header", "true" ).
      option( "inferSchema", "true" ).
      csv( "C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\dummy3.txt" )

    df1.show

    val df2 = df1.groupBy($"course", $"dept").
      agg(collect_set($"status") as "status")

    df2.show

    println(df2.printSchema())



    val df3 = df2.withColumn("finalstatus", when(array_contains($"status", "absent"), "absent")
       when(array_contains($"status","fail"), "failed")
       when(array_contains($"status", "detained"), "failed") otherwise "passed")


    df3.show


   // val convertstatus = udf(sample(_:Array[String]))

   // val df4 = df2.withColumn("newstatus", convertstatus($"status"))

    df2.createOrReplaceTempView("sample")

    spark.sql("select course, dept, case when array_contains(status, 'absent') then 'absent' " +
      " when array_contains(status, 'fail') then 'failed' when array_contains(status, 'detained') then 'failed' " +
      " else 'passed' end as finalstatus from sample").show


  }


  def sample(x: Array[String]): String = x match {
    case y if y.contains("absent") => "absent"
    case y if y.contains("fail") || y.contains("detained") => "failed"
    case y => "passed"
  }

}
