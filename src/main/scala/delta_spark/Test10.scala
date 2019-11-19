package delta_spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions._
import io.delta.tables._

object Test10 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName( "sample21" ).
      master( "local" ).
      getOrCreate

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._


    val df = spark.read.option( "header", "true" ).
      option( "inferSchema", "true" ).
      csv( "C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\cust\\cust3.txt" )

    df.show

/*
    val df2 = df.withColumn("current", lit(true))

    df2.write.format("delta").
      save("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\cust\\cust_delta_cdc")
*/


    val dlt = DeltaTable.forPath("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\cust\\cust_delta_cdc")

    val delta_df = dlt.toDF

    delta_df.show

    val delRecs = delta_df.join(df, df("id") === delta_df("id"), "leftanti").
      drop($"current")

    delRecs.show

    val updRecs = df.join(delta_df, df("id") === delta_df("id")).
      where(df("amt") =!= delta_df("amt")).
      select(df("*"))

    updRecs.show

    val mixRecs = df.select($"id" as "mergekey", $"*", lit(false) as "deleted").
      union(delRecs.select($"id" as "mergekey", $"*", lit(true) as "deleted")).
      union(updRecs.select(lit(null) as "mergekey", $"*", lit(false) as "deleted"))

    mixRecs.show

    dlt.as("dlt_df").
      merge(mixRecs.as("updates"), $"dlt_df.id" === $"mergekey").
      whenMatched($"updates.deleted" === true).
      delete().
      whenMatched($"dlt_df.amt" =!= $"updates.amt").
      updateExpr(Map("current" -> "false")).
      whenNotMatched().
      insertExpr(Map(
        "id" -> "updates.id",
        "name" -> "updates.name",
        "amt" -> "updates.amt",
        "current" -> "true"
      )).execute()

    delta_df.show


    // if we need to delete multiple same id this will give error







  }

}
