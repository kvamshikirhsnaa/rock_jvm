package com.spark.prac

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Aggregator
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object UDAFAgg1 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")

    val df = spark.sparkContext.parallelize(Seq(("a",1.0),("a",2.0),("a",3.0),("b",6.0), ("b", 8.0)))
      .toDF("col1", "col2")

    df.show

    df.groupBy("col1").agg(mean("col2")).show

    df.groupBy("col1").agg(callUDF("percentile_approx", col("col2"), lit(0.5))).show

  }
}
