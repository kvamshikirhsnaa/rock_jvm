package spark_poc


import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions._

import java.time.LocalDateTime


object mysql2 {
  def main(args: Array[String]): Unit = {


    val spark = SparkSession.builder().
      master("local").
      appName("using mysql2").getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")

    val df1 = spark.read.format("jdbc").
      option("url", "jdbc:mysql://localhost:3306/kvk").
      option("dbtable", "employee").
      option("user", "root").
      option("password", "Kenche@21").
      option("inferSchema", "true").
      option("header", "true").load

    df1.show
    df1.printSchema()

    df1.coalesce(1).write.mode("append").csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\delta")


    val df2 = spark.read.csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\delta")

    df2.show
  }
}
