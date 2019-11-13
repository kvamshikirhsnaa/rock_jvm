package com.spark.prac

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._


object Emp {
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
      .toDF("name", "age", "dno", "sal", "day")


    val df2 = df1.filter($"name".isNotNull)

    val spec = Window.partitionBy($"dno")

    val df3 = df2.withColumn("cnt", count($"age") over spec)

    df3.show(30)

    val df4 = df2.groupBy($"dno").agg(sum("sal"), sum("age"))

    df4.show






  }

}
