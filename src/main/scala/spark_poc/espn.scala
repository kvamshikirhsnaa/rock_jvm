package spark_poc

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._

object espn {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.
      builder.
      master("local").
      appName("espn").
      getOrCreate()

    import spark.implicits._
    spark.sparkContext.setLogLevel("ERROR")

    val df = spark.
      read.
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\espn.txt").
      toDF("name", "date")

    df.createOrReplaceTempView("espn")

    df.show

    df.printSchema()
    val df2 = df.select($"name", to_date($"Date", "dd/MM/yyyy") as "newdate")

    df2.show

    df2.createOrReplaceTempView("espnNew")

    spark.sql("select name, to_date(from_unixtime(unix_timestamp(date, 'dd/MM/YYYY'))) as newdate from espn").show



    spark.sql("select distinct(name) from espnNew where newdate between '2009-03-31' and '2019-01-01' ").show

    //spark.sql("select distinct(name), to_date(from_unixtime(unix_timestamp(date, 'dd/MM/yyyy'))) as newdate from espn having newdate between '2009-03-31' and '2019-01-01'  ")

    spark.sql("select year(newdate) from espnNew").show
  }


}
