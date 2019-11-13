package spark_playground

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.types._


case class Emp(name: String, age: Int, dno: Int, sal: Long, dt: String)

object Test2 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master("local").
      appName("sample").getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val empDF = spark.read.option("header", "true").
      option("inferSchema", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\employee.txt").
      as[Emp]

    empDF.show
    empDF.printSchema()

    empDF.na.fill("aaa").show  // filling all String values
    empDF.na.fill(0).show  // filling all Integer values

    empDF.na.fill(Map("name" -> "aaa", "age" -> 20, "dno" -> 20, "sal" -> 100, "dt" -> "2019/01/01" )).show

    empDF.na.drop("any").show  // removes entire rec if any column has null

    empDF.na.drop("all").show  // removes entire rec if all columns has null

    val df = empDF.na.drop("all")

    val df2 = df.withColumn("dt5", to_date($"dt", "yyyy/MM/dd"))

    df2.show
    df2.printSchema()

    val df3 = df.withColumn("dt5", date_format(to_date($"dt", "yyyy/MM/dd"), "yy-MM-dd"))

    df3.show
    df3.printSchema()



    val df4 = df.withColumn("dt5", to_timestamp($"dt", "yyyy/MM/dd"))

    df4.show
    df4.printSchema()

    // to change timezone of timestamp
    val df5 = df.withColumn("dt5", to_utc_timestamp(to_timestamp($"dt", "yyyy/MM/dd"), "UTC"))

    df5.show
    df5.printSchema()

    val df6 = df.withColumn("dt5", unix_timestamp(to_date($"dt", "yyyy/MM/dd")))

    df6.show
    df6.printSchema()

    val df7 = df.withColumn("dt5", unix_timestamp(to_timestamp($"dt", "yyyy/MM/dd")))

    df7.show
    df7.printSchema()

    val df8 = df6.withColumn("dt10", to_date(from_unixtime($"dt5", "yyyy-MM-dd")))

    df8.show
    df8.printSchema()
  }

}
