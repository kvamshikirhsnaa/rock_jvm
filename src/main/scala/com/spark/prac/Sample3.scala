package com.spark.prac

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object Sample3 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    //val schema = StructType(Array(StructField("visitor_id", StringType, true),ArrayType("tracking_id", StringType, true)))

    val df1 = spark.read
      .option("inferSchema", "true")
      .csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\sample3.txt")
      .toDF("visitor_id","tracking_id","email_id")

    df1.show
    df1.printSchema

//    val cols = df.columns.filter(_.startsWith("logic")).map(col(_)) // coalesce

//    val df2 = df1.select($"visitor_id", split($"tracking_id", "&") as "tracking_id",
//      coalesce($"email_id", lit(" ")) as "email_id" )

    val df2 = df1.select($"visitor_id", split($"tracking_id", "&") as "tracking_id",
      $"email_id")

    df2.show

    df2.createOrReplaceTempView("sample")

    val df3 = spark.sql("select visitor_id, email_id, d1.tracking_id from sample lateral view explode(tracking_id) d1 as tracking_id")

    df3.show

    val df4 = df3.groupBy($"visitor_id").agg(sum($"email_id") as "email")

    df4.show

  }

}
