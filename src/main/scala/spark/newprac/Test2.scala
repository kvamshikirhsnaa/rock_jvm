package spark.newprac

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.functions.desc
import org.apache.spark.sql.types._

object Test2 {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val df = spark.range(100).toDF("num")

    //  Random Samples:
    //   Sometimes, you might just want to sample some random records from your DataFrame.
    //   You can do this by using the sample method on a DataFrame, which makes it
    //   possible for you to specify a fraction of rows to extract from a DataFrame
    //   and whether you’d like to sample with or without replacement:

    val seed = 5
    val withReplacement = false
    val fraction = 0.5
    df.sample(withReplacement, fraction, seed).show(15)
    println(df.sample(withReplacement, fraction, seed).count)

    //  Random Splits:
    //    Random splits can be helpful when you need to break up your DataFrame,
    //    well...randomly, in such a way that you cannot guarantee that all records
    //    are in one of the DataFrames from which you’re sampling. This is often
    //    used with machine learning algorithms

    val newseed = 5
    val dataFrames = df.randomSplit(Array(0.25, 0.75), seed)
    dataFrames(0).show(10)
    dataFrames(1).show(10)
    println(dataFrames(0).count() > dataFrames(1).count()) // False

    df.sort($"num".desc).show(5)
    df.orderBy($"num".desc).show(5)
    df.orderBy(desc("num") ).show(5)

//  For optimization purposes, it’s sometimes advisable to sort within each
//  partition before another set of transformations. You can do this by
//  using the sortWithinPartitions method:

    df.sortWithinPartitions($"num" desc_nulls_last).show(5)


//  Repartition and Coalesce:
//    Another important optimization opportunity is to partition the data
//    according to some frequently filtered columns, which control the physical
//    layout of data across the cluster including the partitioning scheme and
//    the number of partitions.
//
//  Repartition will incur a full shuffle of the data, regardless of
//    whether one is necessary. This means that you should typically only
//    repartition when the future number of partitions is greater than your
//    current number of partitions or when you are looking to partition by
//    a set of columns:

//  If you know that you’re going to be filtering by a certain
//    column often, it can be worth repartitioning based on that column:
//    You can optionally specify the number of partitions you would like, too:

    println(df.rdd.getNumPartitions) // 1

    println(df.repartition(5).rdd.getNumPartitions)

//  Coalesce:
//    on the other hand, will not incur a full shuffle and
//    will try to combine partitions. This operation will shuffle your
//    data into five partitions based on the destination country name,
//    and then coalesce them (without a full shuffle):

    println(df.repartition(5, col("num")).
      coalesce(2).rdd.getNumPartitions)

  }
}
