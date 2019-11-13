package myprac.spark

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._


import scala.util.Random
import scala.math.BigDecimal

object Test5 {
  def main(args: Array[String]): Unit = {


    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    spark.conf.set("spark.sql.autoBroadcastJoinThreshold", -1)

    import spark.implicits._


    val tmpWorkshops = Seq(
      ("Warsaw", 2016, 2),
      ("Toronto", 2016, 4),
      ("Toronto", 2017, 1)).toDF("city", "year", "count")

    // there seems to be a bug with nulls
    // and so the need for the following union
    val cityNull = Seq(
      (null.asInstanceOf[String], 2016, 3),
      ("Warsaw", 2017, 2),
      ("Warsaw", 2017, 2),
      ("Warsaw", 2018, 4))
      .toDF("city", "year", "count")

    val workshops = tmpWorkshops union cityNull

    workshops.show


    val df4 = workshops.map(x =>  x.getAs[String]("city"))

    df4.show

    implicit val mapEncoder = org.apache.spark.sql.Encoders.kryo[Map[String, Any]]

    val df5 = workshops.map(x =>  x.getValuesMap[Any](List("city", "year")))

    df5.show

    val q = workshops
      .cube("city", "year")
      .agg(grouping("city"), grouping("year")) // <-- grouping here
      .sort($"city".desc_nulls_last, $"year".desc_nulls_last)

    //q.show

    val query = workshops
      .cube("city", "year")
      .agg(grouping_id()) // <-- all grouping columns used
      .sort($"city".desc_nulls_last, $"year".desc_nulls_last)

    query.show





  }

}
