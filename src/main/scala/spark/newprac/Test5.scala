package spark.newprac

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object Test5 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val df = spark.read.option("header", "true").
      option("inferSchema", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Desktop\\sample.txt")

    df.show

    val df2 = df.withColumn("id", monotonically_increasing_id())

    //df2.show

    println(df2.rdd.partitions.size)

//    df2.write.
//      save("C:\\Users\\Kenche.vamshikrishna\\Desktop\\sample2")

//    val df3 = df2.repartition(3, $"city")


    val dfnew = spark.read.option("header", "true").
      option("inferSchema", "true").
      load("C:\\Users\\Kenche.vamshikrishna\\Desktop\\sample2")

    dfnew.show()

    println(dfnew.rdd.partitions.size)

    val dfnew2 = dfnew.groupBy($"city").agg(sum($"id"))

    println(dfnew2.rdd.partitions.size)

    // dfnew2.rdd.glom().map(x => x.size).collect.foreach(println)

    //dfnew2.rdd.mapPartitions(iter => Iterator(iter.length)).collect().foreach(println)


    val dfnew3 = dfnew.repartition(15, $"city", $"id")

    val dfnew4 = dfnew3.groupBy($"city").agg(sum($"id"))

    dfnew2.rdd.mapPartitions(iter => Iterator(iter.length)).collect().foreach(println)




  }
}
