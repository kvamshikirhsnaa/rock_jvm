package spark_poc

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

import scala.collection.mutable.HashMap
import java.time.LocalDateTime

object Test {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master("local").
      appName("aadhar data analysis").
      getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")
    spark.conf.set("spark.sql.shuffle.partitions", 2)

    val df1 = spark.read.
      option("inferSchema", "true").option("header", "false").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\control.txt").
      toDF("tableName","time")

    df1.show

    val pair1 = df1.withColumn("pair", struct($"tableName",$"time"))
    pair1.show

    val grouppedByTwoFirst = pair1.groupBy("tableName").
      agg(collect_list("pair").alias("lastTwoArray"))

    grouppedByTwoFirst.show

    val structToMap = (value: Seq[Row]) =>
      value.map(v => v.getString(0) -> v.getString(1)).toMap

    val structToMapUDF = udf(structToMap)

    val newDF = grouppedByTwoFirst.select($"tableName", structToMapUDF($"lastTwoArray"))

    newDF.show

    newDF.collect.foreach(println)



  }
}
