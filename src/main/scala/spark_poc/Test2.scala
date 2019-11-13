package spark_poc

import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

import scala.collection.mutable.HashMap
import java.time.LocalDateTime

import org.apache.spark.sql.SparkSession

import scala.util.{Failure, Success, Try}

object Test2 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master("local").
      appName("aadhar data analysis").
      getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")
    spark.conf.set("spark.sql.shuffle.partitions", 2)

    val df1 = spark.read.
      option("inferSchema", "true").option("header", "false").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\control\\control.txt").
      toDF("tname", "lastsink")

    df1.printSchema()

    df1.createOrReplaceTempView("control4")

    spark.sql("select * from control4").show

    def convertTime(xs: String): String = {
      if (xs == null) {
        val sinktime = LocalDateTime.now().toString
        sinktime
      }
      else if (xs < LocalDateTime.now().toString) {
        val sinktime2 = LocalDateTime.now().toString
        sinktime2
      }
      else xs
    }

    val udfConverter = udf(convertTime _)

    val df2 = df1.select($"tname", udfConverter($"lastsink") as "lastsink")

    df2.show

    //df2.write.mode("append").insertInto("control4")

    //spark.sql("select * from control4").show

//    val data = df2.rdd.map(x => x.toString().replace("[", "").replace("]", ""))
//
//    val data2 = data.map(x => (x.split(",")(0), x.split(",")(1)))
//
//    data2.take(2).foreach(println)
//
//    var pair2 = data2.collectAsMap()

  }
}
