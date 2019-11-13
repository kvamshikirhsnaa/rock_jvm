package spark_poc

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.types._

object espn3 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder.
      master("local").
      appName("espn2").
      getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")

    val df = spark.read.option("header", "false").
      option("inferSchema", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\espn6.txt").
      toDF("customTargeting", "dt")

    df.show(5)

    val df2 = df.select(split(regexp_extract($"customTargeting", "swid=\\{?[- A-Za-z0-9]*\\}?", 0), "=")(1) as "swid", to_date(from_unixtime(unix_timestamp($"dt","dd/MM/yyyy"))) as "dt")
    df2.show(5)

    val df3 = df2.select($"swid", when($"dt" isNull, "2019-04-10") otherwise $"dt" as "dt")
    df3.show(5)
    df3.printSchema()
   // df3.select(y($"dt")).show

    val df4 = df3.withColumn("year", year($"dt")).
      withColumn("month", month($"dt")).
      withColumn("day", dayofmonth($"dt"))

    df4.show(10)

  // df4.write.partitionBy("year", "month", "day").
    // save("C:\\Users\\Kenche.vamshikrishna\\Downloads\\espn3")

    val df5 = spark.read.
      load("C:\\Users\\Kenche.vamshikrishna\\Downloads\\espn3").
      toDF("swid","dt","year","month","day")

    df5.show(10)

    val df6 = spark.read.option("inferSchema", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\espn7.txt").
      toDF("swid")

    df6.show

    df5.createOrReplaceTempView("espn6")

    val df7 = df5.join(df6, df5("swid") === df6("swid"), "left_Outer").
     groupBy(df5("swid"),$"month").count.orderBy($"count" desc)

    df6.createOrReplaceTempView("espn7")

    spark.sql("select * from espn6").show(10)
    spark.sql("select * from espn7").show(10)

    spark.sql("select e1.swid,e1.month,count(*) cnt from espn6 e1 left outer join espn7 e2 on e1.swid = e2.swid group by e1.swid, e1.month order by cnt desc limit 20").show






  }
}
