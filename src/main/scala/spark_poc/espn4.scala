package spark_poc

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.types._

object espn4 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder.
      master("local").
      appName("espn2").
      getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")

    val df = spark.read.option("inferSchema","true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\espn7.txt").
      toDF("name")

    df.show

    val df2 = df.select(regexp_extract($"name", "[- A-Za-z0-9]*", 0))

    df2.show
    df2.collect

    df.createOrReplaceTempView("espn")

    spark.sql("select regexp_extract(name, '[- A-Za-z0-9]*', 0) name from espn ").show


    df2.rdd.collect.foreach(println)
  }

}
