package spark_poc

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._


object aadhar {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder.
      master("local").
      appName("aadhar data analysis").
      getOrCreate

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")
    spark.conf.set("spark.sql.shuffle.partitions",2)

    val df = spark.read.
      option("inferSchema","true").option("header","true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\aadhar.txt")

    //df.show

    println(df.count())
    println(df.dropDuplicates.count())

    val df2 = df.filter("Gender is null or Age is null or 'Aadhaar generated' is null")
    df2.show

    val df3 = df.filter($"Gender" isNull)
    df3.show


  }

}
