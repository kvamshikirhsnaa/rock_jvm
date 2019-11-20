package spark_playground

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.log4j.{Level, Logger}

import scala.util.Random



object Test18 {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession.builder().
      master( "local" ).
      appName( "sample" ).getOrCreate()

    spark.sparkContext.setLogLevel( "ERROR" )
    spark.conf.set("spark.sql.autoBroadcastJoinThreshold", -1)

    import spark.implicits._



    val ids = Seq(101, 102, 103, 104, 105, 106, 107, 108, 109, 110)

    def randPurchase = 1000 + Random.nextInt(100)

    def randGen = {
      if (Random.nextBoolean()) Purchase( ids( 0 ), randPurchase )
      else Purchase( ids( Random.nextInt( ids.size ) ), randPurchase )
    }

    val df1 = Seq.fill(1000)(randGen).toDF

    df1.show

    val df2 = Seq((101,"saikrishna"), (102, "aravind"), (103, "narahari"), (104, "prakash"), (105, "anji"), (106, "govind"),
      (107, "anji"), (108, "nani"), (109, "tilak"), (110, "venkat")).toDF("id", "name")

    df2.show

    val joindf = df1.join(df2, Seq("id"))

    println(joindf.rdd.partitions.size)
    println(joindf.rdd.mapPartitions(x => Iterator(x.size)).collect.toList)


    println("Repartition")
    // it send data evenly across all partitions, will perform full shuffle(wide transformation)
    val dfRep1 = joindf.repartition(20)
    println(dfRep1.rdd.partitions.size)
    println(dfRep1.rdd.mapPartitions(x => Iterator(x.size)).collect.toList)

    val dfRep2 = joindf.repartition(20, 'id)
    println(dfRep2.rdd.partitions.size)
    println(dfRep2.rdd.mapPartitions(x => Iterator(x.size)).collect.toList)

    val dfRep3 = joindf.repartition(20, 'amt)
    println(dfRep3.rdd.partitions.size)
    println(dfRep3.rdd.mapPartitions(x => Iterator(x.size)).collect.toList)

    val dfRep4 = joindf.repartition(50, 'id, rand)
    println(dfRep4.rdd.partitions.size)
    println(dfRep4.rdd.mapPartitions(x => Iterator(x.size)).collect.toList)

    println()


    println("Coalesce")
    // it send data oddly  across all partitions, will not perform shuffle operation(narrow transformation)
    // only for decreasing partitions count, takes only Int as argument, don't take any columns
    val dfCoal = joindf.coalesce(20)
    println(dfCoal.rdd.partitions.size)
    println(dfCoal.rdd.mapPartitions(x => Iterator(x.size)).collect.toList)


    println()

    println("repartitionByRange")
    // it will take at least one partition column or expression of column,
    // will do partition within that given integer number
    val dfRepByRng1 = joindf.repartitionByRange(10, 'id)
    println(dfRepByRng1.rdd.partitions.size)
    println(dfRepByRng1.rdd.mapPartitions(x => Iterator(x.size)).collect.toList)

    val dfRepByRng2 = joindf.repartitionByRange(11, 'id)
    println(dfRepByRng2.rdd.partitions.size)
    println(dfRepByRng2.rdd.mapPartitions(x => Iterator(x.size)).collect.toList)

    val dfRepByRng3 = joindf.repartitionByRange(20, 'id)
    println(dfRepByRng3.rdd.partitions.size)
    println(dfRepByRng3.rdd.mapPartitions(x => Iterator(x.size)).collect.toList)

    val dfRepByRng4 = joindf.repartitionByRange(10, 'amt)
    println(dfRepByRng4.rdd.partitions.size)
    println(dfRepByRng4.rdd.mapPartitions(x => Iterator(x.size)).collect.toList)

    val dfRepByRng5 = joindf.repartitionByRange(20, 'amt)
    println(dfRepByRng5.rdd.partitions.size)
    println(dfRepByRng5.rdd.mapPartitions(x => Iterator(x.size)).collect.toList)

    val dfRepByRng6 = joindf.repartitionByRange(20, 'id, rand)
    println(dfRepByRng6.rdd.partitions.size)
    println(dfRepByRng6.rdd.mapPartitions(x => Iterator(x.size)).collect.toList)

    // sending data across all partitions equally
    val dfRepByRng7 = joindf.repartitionByRange(30, 'id, rand)
    dfRepByRng7.show
    println(dfRepByRng7.rdd.partitions.size)
    println(dfRepByRng7.rdd.mapPartitions(x => Iterator(x.size)).collect.toList)


    val newdf = dfRepByRng7.withColumn("id", regexp_extract('id, "[0-9]+", 0))
    newdf.show
    println(newdf.rdd.partitions.size)
    println(newdf.rdd.mapPartitions(x => Iterator(x.size)).collect.toList)

    val newdf2 = newdf.groupBy('id).agg(max('amt) as "amt")

    newdf2.show
    println(newdf2.rdd.partitions.size)
    println(newdf2.rdd.mapPartitions(x => Iterator(x.size)).collect.toList)




















  }

}
