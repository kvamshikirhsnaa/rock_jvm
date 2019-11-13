package myprac.spark


import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._

import scala.util.Random
import scala.math.BigDecimal

object Test4 {
  def main(args: Array[String]): Unit = {


    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    spark.conf.set("spark.sql.autoBroadcastJoinThreshold", -1)

    import spark.implicits._


    val df = spark.read.
      load("C:\\Users\\Kenche.vamshikrishna\\Desktop\\sample2")

    df.show

//    df.write.format("delta").mode("append").
//      save("C:\\Users\\Kenche.vamshikrishna\\Desktop\\sample3")


    val df2 = df.withColumn("country", lit("India"))

//    df2.write.option("mergeSchema", "true").
//      format("delta").
//      mode("append").
//      save("C:\\Users\\Kenche.vamshikrishna\\Desktop\\sample3")


    val df3 = spark.read.format("delta").
      load("C:\\Users\\Kenche.vamshikrishna\\Desktop\\sample3")

    df3.show

    val df4 = df3.withColumn("part", spark_partition_id())


    df4.groupBy($"part").count.show()


    val df5 = df3.repartition(20,$"city", rand())

    val df6 = df5.withColumn("part", spark_partition_id())

    println(df6.rdd.partitions.size)

    val df7 = df6.groupBy($"part").count

    println(df7.rdd.partitions.size)

    val df8 = df6.groupBy($"city").count

    println(df8.rdd.partitions.size)








  }

}
