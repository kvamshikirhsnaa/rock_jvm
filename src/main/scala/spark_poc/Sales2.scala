package spark_poc

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._


object Sales2 {
  def main(args: Array[String]): Unit = {


    val spark = SparkSession.builder().
      appName("sample").
      master("local").getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val df = spark.read.option("header", "false").
      option("inferSchema", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\sales3.txt").
      toDF("id", "price")

    df.show

    val spec = Window.orderBy($"id").rowsBetween(-2, Window.currentRow)

    val tot = sum($"price") over spec

    val df2 = df.withColumn("amt", tot)

    df2.show

    val lag2 = lag($"price",2,0) over Window.orderBy($"id")

    val df3 = df.withColumn("amt", when(lag2 === 0, $"price") otherwise tot)

    df3.show

    df.createOrReplaceTempView("sales")

    spark.sql("select id, price, sum(price) over (order by id rows between 2 preceding and current row) as tot from sales").show

  }
}