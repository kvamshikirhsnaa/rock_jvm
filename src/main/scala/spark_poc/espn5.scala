package spark_poc

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.types._

object espn5 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder.
      master("local").
      appName("espn2").
      getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")

  // spark.sql("create table newEspn(video_location struct<id:int, video_title: string>) row format delimited fields terminated by ',' collection items terminated by ':' ")

    // spark.sql("load data local inpath 'C:\\Users\\Kenche.vamshikrishna\\Downloads\\espn9.txt' into table newEspn")

    val df = spark.read.
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\espn9.txt").
      toDF("id", "video_title")

    df.show

    val df2 = df.select(concat($"id",lit(","),$"video_title") as "video_location")

    df2.show




  }

}
