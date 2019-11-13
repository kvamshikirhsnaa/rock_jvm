package delta_spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions._

object Test4 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName( "sample21" ).
      master( "local" ).
      getOrCreate

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._

    val df = spark.read.option("header", "true").
      option("inferSchema", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\emp4.txt")

/*

    val df2 = df.select($"id", $"name", $"loc" as "curr_loc",
      lit(null).cast(StringType) as "prev_loc", $"start_dt",
      lit(null).cast(TimestampType) as "end_dt")

    df2.repartition(1).write.
      option("header", "true").mode("append").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\emp_csv")

*/


    val empDF = spark.read.option("header", "true").
      option("inferSchema", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\emp_csv")

    val spec = Window.partitionBy('id).orderBy('start_dt.desc)

    val empDF2 = empDF.withColumn("rnk", row_number() over spec).
      where('rnk === 1).drop("rnk")

    empDF2.show

    val joinedDF = df.join(empDF2, Seq("id"), "leftouter")

    joinedDF.show

    val newDF = joinedDF.select('id, df("name"), 'loc as "curr_loc", when(empDF2("curr_loc").isNull, lit(null))
      otherwise empDF2("curr_loc") as "prev_loc", df("start_dt"),
      when(empDF2("start_dt").isNull, lit(null)) otherwise empDF2("start_dt") as "end_dt")

    newDF.show

    newDF.repartition(1).write.option("header", "true").
      mode("append").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\emp_csv")






  }

}
