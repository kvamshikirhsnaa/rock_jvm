package com.spark.prac

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._

object Test3 {
  def main(args: Array[String]): Unit = {


    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val df1 = spark.read
      .option("inferSchema", "true")
      .csv("C:\\Users\\Kenche.vamshikrishna\\Desktop\\test.txt")
      .toDF("id", "state", "date")

    df1.show
    df1.printSchema()

//    val spec = Window.partitionBy($"id").orderBy($"date" desc)
//
//
//    val df2 = df1.select($"id", $"state", $"date", dense_rank() over spec as "rnk")
//
//    df2.show
//
//    val df3 = df2.select()

    df1.createOrReplaceTempView("test3")

    val df3 = spark.sql("select * from (select id, state, date, dense_rank() over (partition by id,state order by date desc) rnk from test3) q where rnk=1")

    df3.show

    df3.createOrReplaceTempView("test4")

    val df4 = spark.sql("select * from (select id, state, date, dense_rank() over (partition by id order by date desc) rnk from test4) q")

    df4.show

    df4.createOrReplaceTempView("test5")

    val df5 = spark.sql("select id, collect_list(state) as state, collect_list(date) as date from test5 group by id")

    df5.show

    val df6 = df5.select($"id", $"state".getItem(0) as "state", $"date".getItem(0) as "date")

    df6.show

    val df7 = df5.select($"id", when(array_contains($"state", "R") , $"state".getItem()))
//
//    withColumn('contains_chair
//    ', array_contains(df_new.collectedSet_values, 'chair') ).show
//



  }
}