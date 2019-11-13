package spark.newprac

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object Test4 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

//    val df = spark.read.option("header", "true").
//      option("inferSchema", "true").
//      csv("C:\\Users\\Kenche.vamshikrishna\\Desktop\\sample.txt")
//
//    df.show
//
//    println(df.printSchema())
//
//    val columnName = "BIRTH_DT"
//
//    val updatedDF = df.withColumn(columnName, unix_timestamp(col(columnName), "yyyy-MM-dd HH:mm:ss").cast("timestamp"))
//
//    updatedDF.show
//
//    println(updatedDF.printSchema())

  }

}
