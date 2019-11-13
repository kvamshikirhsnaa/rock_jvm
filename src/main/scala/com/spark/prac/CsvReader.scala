package com.spark.prac

import com.spark.prac.Spark.spark
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types
import org.apache.spark.sql.functions

object Spark {
  val spark = SparkSession.builder().
    appName("sample").master("local").
    getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")

  import spark.implicits._
}

class Test {
  import Spark._

  val df = spark.read.
    option("inferSchema", "true").
    csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\cust.txt")

  df.write.save("C:\\Users\\Kenche.vamshikrishna\\Downloads\\Custmer")
}

object CsvReader {
  def main(args: Array[String]): Unit = {

    val t = new Test
    t.df.show
  }
}
