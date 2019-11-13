package com.spark.prac

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object FoldLeftAgg5 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._


    val lst =
      List("itemA,CATs,2,4",
        "itemA,CATS,3,1",
        "itemB,CATQ,4,5",
        "itemB,CATQ,4,6",
        "itemC,CARC,5,10")


    val df = spark.sparkContext.parallelize(lst).toDF("value")

    df.show

    val df2 = df.withColumn("arr",split($"value",","))

    df2.show

    val df3 = df2.withColumn("item", $"arr"(0)).withColumn("category", $"arr"(1))
      .withColumn("amount", $"arr"(2).cast("Int")).withColumn("price", $"arr"(3).cast("Int"))
      .drop("value","arr")

    df3.show















  }
}