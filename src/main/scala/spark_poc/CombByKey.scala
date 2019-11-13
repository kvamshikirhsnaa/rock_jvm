package spark_poc

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object CombByKey {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val df1 = spark.read.
      option("inferSchema", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Desktop\\Spark\\sample.txt")

    df1.show

    //df1.select(count($""))

  }

}
