package spark_poc

import org.apache.spark.sql.SparkSession

object Sample2 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.
      builder.
      master("local").
      appName("working with csv").
      getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")
    spark.conf.set("spark.sql.shuffle.partitions", 2)

    val df = spark.read.option("header","true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\aadhar.txt")

    df.show
  }

}
