package spark_poc


import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions._

import java.time.LocalDateTime

object mysql {
  def main(args: Array[String]): Unit = {


    val spark = SparkSession.builder().
      master("local").
      appName("using mysql").getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")

    val custSchema = StructType(Array(StructField("tname", StringType, true),
      StructField("lastsink",StringType,true)))

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

    val df1 = spark.read.format("jdbc").
      option("url","jdbc:mysql://localhost:3306/kvk").
      option("dbtable","control3").
      option("user","root").
      option("password","Kenche@21").schema(custSchema).load.
      toDF("tname", "lastsink")

    df1.show

    val rdd1 = df1.rdd.map(x => x.toString().replace("[", "").
      replace("]", ""))

    val rdd2 = rdd1.map(x => (x.split(",")(0), x.split(",")(1)))

    var pair3 = rdd2.collectAsMap()

    if (pair3.get("aadhar1").get == None) {
      val aadharDF1 = spark.read.format("jdbc").
        option("url", "jdbc:mysql://localhost:3306/kvk").
        option("dbtable", "employee").
        option("user", "root").
        option("password", "Kenche@21").
        option("inferSchema", "true").
        option("header", "true").load
    }
    else {
      val aadharDF1 = spark.read.format("jdbc").
        option("url", "jdbc:mysql://localhost:3306/kvk").
        option("dbtable", "employee").
        option("user", "root").
        option("password", "Kenche@21").
        option("inferSchema", "true").
        option("header", "true").load.
        where($"sink" > pair3.get("aadhar1").get)
    }


     val df2 = df1.select($"tname", udfConverter($"lastsink") as "lastsink")

    //val df3 = df2.

     df2.write.format("jdbc").
       option("url","jdbc:mysql://localhost:3306/kvk").
       option("dbtable", "control3").
       option("user","root").
       option("password","Kenche@21").mode("append").save()

    val rdd4 = df2.rdd.map(x => x.toString().replace("[", "").
      replace("]", ""))

    val rdd5 = rdd4.map(x => (x.split(",")(0), x.split(",")(1)))

    var pair2 = rdd5.collectAsMap()

    println(pair2)

    if (pair2.get("aadhar1").get == None) {
      val aadharDF1 = spark.read.format("jdbc").
        option("url", "jdbc:mysql://localhost:3306/kvk").
        option("dbtable", "employee").
        option("user", "root").
        option("password", "Kenche@21").
        option("inferSchema", "true").
        option("header", "true").load
    }
    else {
      val aadharDF2 = spark.read.format("jdbc").
        option("url", "jdbc:mysql://localhost:3306/kvk").
        option("dbtable", "employee").
        option("user", "root").
        option("password", "Kenche@21").
        option("inferSchema", "true").
        option("header", "true").load.
        where($"sink" > pair2.get("aadhar1").get)
    }










  }

}
