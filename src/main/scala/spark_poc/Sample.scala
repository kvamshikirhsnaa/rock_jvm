package spark_poc

import org.apache.spark.sql.SparkSession

object Sample {
    def main(args: Array[String]): Unit = {
      val spark = SparkSession.builder.
        master("local").
        appName("spark-prac").
        getOrCreate()

      spark.sparkContext.setLogLevel("ERROR")
      spark.conf.set("spark.sql.shuffle.partitions","2")

      val nums = 1 to 20 by 2
      val rdd = spark.sparkContext.parallelize(nums)
      rdd.collect.foreach(println)

    }

}
