package com.spark.prac

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._

object Test4 {
  def main(args: Array[String]): Unit = {


    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val df1 = spark.read
      .option("inferSchema", "true")
      .csv("C:\\Users\\Kenche.vamshikrishna\\Desktop\\test.txt")
      .toDF("id", "state", "date")

      df1.show
      df1.printSchema()

    val df2 = df1.withColumn("newstate", when($"state" === "R", 0) otherwise 1)

//    df2.createOrReplaceTempView("test3")
//
//    spark.sql("select * from (select id, state, date, newstate, dense_rank() over " +
//      "partition by id order by newstate , date desc) as rnk from test3) q where rnk=1 order by id").show

    val spec = Window.partitionBy($"id").orderBy($"newstate" , $"date".desc)

    val df3 = df2.withColumn("rnk", dense_rank() over spec).where($"rnk" === 1).
      drop("rnk", "newstate").orderBy($"id")

    df3.show

//      val spec = Window.partitionBy($"id").orderBy($"state" desc , $"date" desc)
//
//      df1.withColumn("rnk", dense_rank() over spec).where($"rnk" === 1).show()
//          //drop("rnk", "newstate").orderBy($"id")






//    val df3 = spark.sql("select id, collect_list(state) as state, collect_list(date) as date from test4 group by id")
//
//    df3.show
//
//    val df4 = df3.withColumn("ind",array_position($"state", "R"))
//
//    df4.show
//
//    df4.printSchema()

//    val df5 = df3.select($"id", when(array_contains($"state", "R"), "R") otherwise("P") as "state", when($"state" === "R", $"date".getItem(getIndex(($"ind" - 1), )) otherwise( $"date".getItem(0)))
//
//    df5.show


//   UDF:
// val getIndex = udf((text: String, featuredText: Seq[String]) => {
//  featuredText.indexOf(text)
// })
//    val df4 = df3.select($"id", $"state", $"date", getIndex("R", $"state"))





  }
}