package spark_poc

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._

object Sales {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample").
      master("local").getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val df = spark.read.option("header", "true").
      //option("inferSchema", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\sales.txt")

    df.printSchema()

    val df2 = df.withColumn("time2", lag($"time",2) over (Window.orderBy($"time")))

    df2.show




    val df3 = df2.select(when(datediff($"time2", $"time") === 2, collect_list($"price").over(Window.partitionBy("time"))))

    df3.show(50)

    val df5 = df2.select(collect_list($"price").over(Window.partitionBy(datediff($"time2", $"time"))))

    df5.show
    df5.rdd.collect.foreach(println)

    val df6 = df2.select(collect_list($"price").over(Window.partitionBy(when(datediff($"time2", $"time") === 3, datediff($"time2", $"time"))).orderBy("time").rowsBetween(Window.unboundedPreceding & Window.unboundedFollowing, Window.currentRow)))

    df6.rdd.collect.foreach(println)

   // df3.select(datediff($"time2", $"time")).show(30) // datediff gives number of days difference

   // df3.select(datediff(lit("2019-01-01"), lit("2019-01-07"))).show

   // val spec = Window.orderBy("time").rowsBetween(Window.unboundedPreceding & Window.unboundedFollowing, Window.currentRow)

   // df.createOrReplaceTempView("sales")

//    val df2 = df.withColumn("time2", $"time").withColumn("price2", $"price").
//      drop("time", "price")
//
//    val df3 = df.crossJoin(df2)

   // spark.sql("select (CASE WHEN diff(CAST(s1.`time` AS DATE), CAST(s2.`time` AS DATE)) > 2)
   // THEN collect_list(s1.price) ELSE s1.`price` END AS `amt` from sales s1 join sales s2 on (s1.time == s2.time)")

  }
}