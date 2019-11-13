package spark_poc


import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

import scala.collection.mutable._
import java.time.LocalDateTime

object mysql5 {
  def main(args: Array[String]): Unit = {


    val spark = SparkSession.builder().
      master("local").
      appName("using mysql4").getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")


    val pair = Map("table1" -> " ", "table2" -> " ")

    println(pair)
    println(pair.get("table1"))
    println(pair.get("table1").get == " ")

    val aadharDF1 = spark.read.format("jdbc").
      option("url", "jdbc:mysql://localhost:3306/kvk").
      option("dbtable", "employee").
      option("user", "root").
      option("password", "Kenche@21").
      option("inferSchema", "true").
      option("header", "true").load

    val aadharDF2 = spark.read.format("jdbc").
      option("url", "jdbc:mysql://localhost:3306/kvk").
      option("dbtable", "employee").
      option("user", "root").
      option("password", "Kenche@21").
      option("inferSchema", "true").
      option("header", "true").load.
      where($"sink" > pair.get("table1").get)

    // aadharDF2.show

    if (pair.get("table1").get == " ") {

      aadharDF1.show

      aadharDF1.write.format("jdbc").
        option("url","jdbc:mysql://localhost:3306/kvk").
        option("user","root").
        option("password","Kenche@21").
        option("dbtable", "empnew2").
        mode("append").
        save()
    }
    else {

      aadharDF2.write.format("jdbc").
        option("url","jdbc:mysql://localhost:3306/kvk").
        option("user","root").
        option("password","Kenche@21").
        option("dbtable", "empnew2").
        mode("append").
        save()

      pair.update("table1",(LocalDateTime.now()).toString)
    }


  }

}


