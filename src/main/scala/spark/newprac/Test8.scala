package spark.newprac

import org.apache.spark.sql.{Row, DataFrame,SparkSession}
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import scala.util.Random

object Test8 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    //spark.conf.set("spark.sql.shuffle.partitions", 200)

    import spark.implicits._

    val df1 = spark.read.option("header", "true").
      option("inferSchema", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Desktop\\fact.txt")

    val df2 = df1

    println(df1.rdd.partitions.size)

    val df3 = df1.groupBy($"id").agg(sum($"value"))

    println(df3.rdd.partitions.size)

    val df4 = df1.join(df2,df1("id") === df2("id"))

    println(df4.rdd.partitions.size)

    val df5 = df4.repartition(300, df1("id"))

    val df6 = df5.groupBy(df1("id")).agg(sum(df1("value")))

    println(df6.rdd.partitions.size)

    val df7 = df6.join(df6,df1("id") === df2("id"))

    println(df7.rdd.partitions.size)


  }

}
