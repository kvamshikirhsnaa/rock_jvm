package spark_poc

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object newEspn {
  def main(args: Array[String]) = {

    val spark = SparkSession.builder.
      appName("espn").
      master("local").
      getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")

    val df1 = spark.read.
      option("header", "true").
      option("inferSchema", "true").
      option("delimiter", ":").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\Double Click Raw_edit.csv")

    println(df1.count)
    println(df1.distinct.count)


    df1.show
    df1.printSchema

    val df2 = df1.groupBy($"col25", $"col28").count()
    val df3 = df1.select($"col25",explode(split($"col30","\\|")) as "col30").
      groupBy($"col25", $"col30").count

    df3.show


    val df4 = df1.select("col10")

    df4.show

    val df5 = df4.select(split($"col10", ";"))

    df5.show

    def sample(x: String): Map[String, String] = {
      x.split(";").
        map(x => x.split("=")).
        map { case Array(k,v) => (k, v)}.
        toMap
    }

    val str_to_map = udf(sample _)

    val df6 = df1.select(str_to_map($"col10") as "col10")

    df6.show

    val df7 = df6.select(explode($"col10") as Seq("k","v"))

    df7.show

    println(df7.count)
    println(df7.distinct.count)

    df1.select(coalesce($"col34", $"col33")).show








  }

}




