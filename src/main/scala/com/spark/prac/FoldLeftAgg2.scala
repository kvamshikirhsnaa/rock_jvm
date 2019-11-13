package com.spark.prac

import org.apache.spark.sql.{Row, Column,SparkSession, DataFrame}
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._


object FoldLeftAgg2 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample").master("local").
      getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._


    val df = spark.sparkContext.parallelize(Seq(
      ("r1", 1, 1),
      ("r2", 6, 4),
      ("r3", 4, 1),
      ("r4", 1, 2)
    )).toDF("ID", "a", "b")

    val df2 = df.select($"ID", df("a").cast("Int"), df("b").cast("Int"))

    df.show

    val c = when($"a" === $"b" === 1, $"a" + $"b") when((($"a" === 1) || ($"b" === 1)), 1) otherwise 0

    val df3 = df2.withColumn("C", c)

    df3.show

    val ones = Seq("a", "b").map(x => when(col(x) === 1, 1).otherwise(0)).reduce(_ + _)

    df2.withColumn("ones", ones).show

    def countOnes(cols: Column*) = cols.foldLeft(lit(0)){
      (cnt, current) => when (current === 1, cnt + 1)  otherwise cnt
    }

    df.withColumn("D", countOnes($"a", $"b")).show


  }
}
