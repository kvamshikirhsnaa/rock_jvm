package delta_spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions._
import io.delta.tables._

object Test9 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName( "sample21" ).
      master( "local" ).
      getOrCreate

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._


    val df = spark.read.option("header", "true").
      option("inferSchema", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\cust\\cust3.txt")

/*

    df.write.format("delta").
      save("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\cust\\cust_delta")

*/

    val dlt = DeltaTable.forPath("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\cust\\cust_delta")

    val delta_df = dlt.toDF

    delta_df.show

    val delRecs = delta_df.join(df, Seq("id"), "leftanti")

    delRecs.show

    val mixRecs = df.select($"*", lit(false) as "deleted").
      union(delRecs.select($"*", lit(true) as "deleted"))

    dlt.as("dlt_df").
      merge(mixRecs.as("updates"), $"dlt_df.id" === $"updates.id").
      whenMatched($"updates.deleted" === true).
      delete().
      whenMatched($"dlt_df.amt" =!= $"updates.amt").
      updateExpr(Map("amt" -> "updates.amt")).
      whenNotMatched().
      insertExpr(Map(
        "id" -> "updates.id",
        "name" -> "updates.name",
        "amt" -> "updates.amt"
      )).execute()

    delta_df.show


  }

}
