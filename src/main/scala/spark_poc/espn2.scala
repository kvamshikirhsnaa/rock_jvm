package spark_poc

import java.lang.Integer

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.types._

object espn2 {
  def main(args: Array[String]): Unit ={

    val spark = SparkSession.builder.
      master("local").
      appName("espn2").
      getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")

    val df = spark.read.
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\espn2.txt").
      toDF("name", "date")

    df.createOrReplaceTempView("espn3")

    //spark.sql("select distinct(*) from espn3").show

    //spark.sql("select name,date,count(*) cnt from espn3 group by name,date having cnt >1").show

    //spark.sql("(select * from (select name,date,dense_rank() over (partition by name,date order by name) rnk from espn3) r where rnk > 1)").show
    //spark.sql("(select * from (select name,date,row_number() over (partition by name,date order by name,date) rnk from espn3) r where rnk > 1)").show

    val schema = StructType(Array(
      StructField("id", IntegerType, true),
      StructField("time_spent", DoubleType, true)
    ))

    val df2 = spark.read.schema(schema).
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\espn3.txt").
      toDF("user","time_spent")


    df2.show
    df2.printSchema

    df2.createOrReplaceTempView("espn4")

    spark.sql("select e1.user, e1.time_spent, e2.avg from espn4 e1 join (select avg(time_spent) avg from espn4) e2 where e1.time_spent > e2.avg").show

    // spark.sql("select user,time_spent from espn4 where time_spent > select avg(time_spent) from espn4")


    val df3 = spark.read.option("inferSchema", "true").option("header", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\espn4.txt")


    df3.select(split(regexp_extract($"customTargeting", "swid=\\{?[- A-Za-z0-9]*\\}?", 0), "=")(1) as "customTargeting").show

    df3.createOrReplaceTempView("espn5")

    spark.sql("select split(regexp_extract(customTargeting, 'swid=(.)*', 0), '=')[1] as customTargeting from espn5").show

    spark.sql("select regexp_extract(customTargeting, 'swid=(.)*', 0) customeTargeting from espn5").show

    val df4 = spark.read.option("header","false").
      option("inferSchema","true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\espn5.txt").
      toDF("pagehome")

    val df5 = df4.select(split($"pagehome",":")(2) as "pagehome").filter($"pagehome" === "home")

    df5.count
    df5.show

    df4.createOrReplaceTempView("espn6")

    //spark.sql("select split(pagehome, ':')[2] pagehome from espn6 where pagehome='home' ").show

    spark.sql("select split(pagehome, ':')[2] pagehome, count(*) cnt from espn6 group by pagehome having pagehome = 'home'").show

  }



}
