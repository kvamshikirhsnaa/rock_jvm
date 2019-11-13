package com.spark.prac

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object AggFun2 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val df1 = spark.read
      .option("inferSchema", "true")
      .csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\employee.txt")
      .toDF("name","age","dno","sal","day")

    df1.show

    val newDF = df1.filter($"name" isNotNull)

    newDF.show

//    newDF.select(asc_nulls_last("name"),asc_nulls_last("age"),
//      asc_nulls_last("dno"), asc_nulls_last("sal")).show


//    Window Functions:
//
//    A _group-by_ takes data, and every row can go only into one grouping.
//    A window function calculates a return value for every input row of a table
//    based on a group of rows, called a `frame`. Each row can fall into one or more frames.
//
//    Spark supports three kinds of window functions: ranking functions,
//    analytic functions, and aggregate functions.

    val df2 = newDF.withColumn("date", to_date($"day","yyyy/mm/dd")).drop($"day")

    df2.show


    val spec = Window.partitionBy($"dno")
      .orderBy($"sal" desc)
      .rowsBetween(Window.unboundedPreceding, Window.currentRow)

    val maxSal = max($"sal") over spec

    val rnk = rank() over spec

    val dns_rnk = dense_rank() over spec

    df2.where($"dno" isNotNull).orderBy($"dno")
      .select($"name", $"dno", $"date", $"sal", rnk as "rnk", dns_rnk as "dns_rnk", maxSal as "maxSal").show

    val df3 = df2.filter("name is not null and dno is not null and sal is not null and date is not null")

    df3.show
























  }

}
