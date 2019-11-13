package spark.newprac

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import scala.util.Random

object Test6 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._


    val df = spark.read.option("header", "true").
      option("inferSchema", "true").
      load("C:\\Users\\Kenche.vamshikrishna\\Desktop\\sample2")

    val df2 = df.repartition(15, $"city")

    val df3 = df2


    val df4 = df2.join(df3, df2("city") === df3("city"))

    df4.show

    println(df4.rdd.partitions.size)

    // df4.rdd.mapPartitions(iter => Iterator(iter.length)).collect.foreach(println)

    //df4.rdd.glom().map(x => x.size).collect.foreach(println)



  }

}
