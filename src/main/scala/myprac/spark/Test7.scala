package myprac.spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}

import scala.util.Random
import scala.math.BigDecimal


object Test7 {
  def main(args: Array[String]): Unit = {


    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._


    val data = spark.range(0, 100)

    //data.write.format("delta").save("C:/Users/Kenche.vamshikrishna/Desktop/Spark/Delta/sample")


//    spark.read.format("delta").option("timestampAsOf", timestamp_string).load("path")
//    spark.read.format("delta").option("versionAsOf", version).load("path")

//    For timestamp_string, only date or timestamp strings are accepted.
//      For example, "2019-01-01" and "2019-01-01'T'00:00:00.000Z".

    spark.read.format("delta").option("versionAsOf", 0).
      load("C:/Users/Kenche.vamshikrishna/Desktop/sample3")  // returns specified version data

    spark.read.format("delta").
      load("C:/Users/Kenche.vamshikrishna/Desktop/sample3")   // returns latest version data

    val dfold = spark.read.format("delta").option("versionAsOf", 0).
      load("C:/Users/Kenche.vamshikrishna/Desktop/sample3")

    println(dfold.printSchema())

//    dfold.write.format("delta").mode("overwrite").
//      save("C:/Users/Kenche.vamshikrishna/Desktop/sample3")

//    spark.read.format("delta").
//      load("C:/Users/Kenche.vamshikrishna/Desktop/sample3").show
//
//    spark.read.format("delta").option("versionAsOf", 0).
//      load("C:/Users/Kenche.vamshikrishna/Desktop/sample3").show
//
//    spark.read.format("delta").option("versionAsOf", 1).
//      load("C:/Users/Kenche.vamshikrishna/Desktop/sample3").show
//
//    spark.read.format("delta").option("versionAsOf", 2).
//      load("C:/Users/Kenche.vamshikrishna/Desktop/sample3").show

    val schema2 = StructType(List(StructField("city", StringType, true),StructField("id", LongType, true), StructField("country", StringType, true)))

    val dfnew2 = spark.read.option("header", "true").schema(schema2).
      csv("C:/Users/Kenche.vamshikrishna/Desktop/testnew")

    val dfnew3 = dfnew2.withColumn("rand", lit(Random.alphanumeric.take(5).mkString("")))

    val dfnew4 = dfnew3.withColumn("city", when($"city" === "Bangalore", "Bengaluru"))

//    dfnew4.write.format("delta").mode("overwrite").
//      option("mergeSchema", "true").
//      save("C:/Users/Kenche.vamshikrishna/Desktop/sample3")

    spark.read.format("delta").
      load("C:/Users/Kenche.vamshikrishna/Desktop/sample3").show

    val df5 = spark.read.option("header","true").option("inferSchema", "true").
      csv("C:/Users/Kenche.vamshikrishna/Desktop/testnew")

    df5.write.format("delta").mode("overwrite").
      option("overwriteSchema", "true").
      save("C:/Users/Kenche.vamshikrishna/Desktop/sample3")

    val df6 = spark.read.format("delta").
      load("C:/Users/Kenche.vamshikrishna/Desktop/sample3")

    println(df6.printSchema())

    val df7 = spark.read.format("delta").option("versionAsOf", 1).
      load("C:/Users/Kenche.vamshikrishna/Desktop/sample3")

    println(df7.printSchema())





















  }

}
