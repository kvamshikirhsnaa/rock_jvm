package spark.newprac

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.functions.desc
import org.apache.spark.sql.types._

object Sample2 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    val df1 = spark.read.
      load("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\aadharPar").
      repartition(6, col("State"))

    val df2 = spark.read.
      load("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\aadharPar2").
      repartition(6)

    println(df1.rdd.getNumPartitions)
    println(df2.rdd.getNumPartitions)

    println(df1.count())
    println(df2.count())

    val rdd = df1.rdd.mapPartitions(x => Iterator(x.length))

    println(rdd.collect.foreach(println))


    val rdd2 = df2.rdd.mapPartitions(x => Iterator(x.length))

    println(rdd2.collect.foreach(println))


    val rdd3 = df2.rdd.mapPartitions(x => x.map(a => a))

    println(rdd3.take(50).foreach(println))
















  }
}
