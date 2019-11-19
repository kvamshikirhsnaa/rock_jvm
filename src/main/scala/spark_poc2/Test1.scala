package spark_poc2

import org.apache.spark.sql.SparkSession


// spark CBO

object Test1 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master("local").appName("sample").
      getOrCreate()

    import  spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")
    //spark.conf.set("spark.sql.autoBroadcastJoinThreshold", -1)

    //println(spark.conf.get("spark.sql.cbo.enabled")) // by default it is false

    val df1 = spark.range(10000000)
    val df2 = spark.range(10000000).withColumn("twice", 'id * 2)
    val df3 = spark.range(1000)

//    val joindf1 = df1.join(df2, Seq("id")).join(df3, Seq("id"))
//
//    joindf1.show
//    joindf1.explain()
    // when cbo disabled
/*    == Physical Plan ==
      *(4) Project [id#0L, twice#4L]
    +- *(4) BroadcastHashJoin [id#0L], [id#7L], Inner, BuildRight
    :- *(4) Project [id#0L, twice#4L]
    :  +- *(4) SortMergeJoin [id#0L], [id#2L], Inner
      :     :- *(1) Range (0, 10000000, step=1, splits=1)
    :     +- *(2) Project [id#2L, (id#2L * 2) AS twice#4L]
    :        +- *(2) Range (0, 10000000, step=1, splits=1)
    +- BroadcastExchange HashedRelationBroadcastMode(List(input[0, bigint, false]))
    +- *(3) Range (0, 1000, step=1, splits=1)*/

//    println(joindf1.count)  // 1000


    // cbo enabled: cbo work on tables only, we have to analyze statistics of table after enable
    spark.conf.set("spark.sql.cbo.enabled", "true")

    df1.createOrReplaceTempView("huge")
    df2.createOrReplaceTempView("twice")
    df3.createOrReplaceTempView("small")

/*
    spark.sql("analyze table huge compute statistics")
    spark.sql("analyze table twice compute statistics")
    spark.sql("analyze table small compute statistics")
*/

    val joindfnew1 = df1.join(df2, Seq("id")).join(df3, Seq("id"))

    joindfnew1.explain()
    println(joindfnew1.count())










  }

}
