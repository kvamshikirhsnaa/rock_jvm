package delta_spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions._
import io.delta.tables._


object Test6 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName( "sample21" ).
      master( "local" ).
      getOrCreate

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._

    val df = spark.read.option("header", "true").
      option("inferSchema", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\emp2.txt")

    df.show



/*
        val df2 = df.withColumn("end_dt", lit(null).cast(TimestampType)).
          withColumn("current", lit("true"))

        df2.show
        df2.printSchema

        df2.write.mode("append").format("delta").
          save("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\delta_op_new2")
*/


    val dlt = DeltaTable.forPath("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\delta_op_new2")
    dlt.toDF.show

    val newLocToInsert = df.as("updates").join(dlt.toDF.as("customers"), Seq("id")).
      where("customers.current = true and updates.loc <> customers.loc")

    val stagingUpdates = newLocToInsert.
      selectExpr("NULL as mergekey", "updates.*").
      union(df.selectExpr("id as mergekey", "*"))


    stagingUpdates.show
    stagingUpdates.printSchema()



    dlt.as("customers").
      merge(stagingUpdates.as("staging_updates"), $"customers.id" === $"mergekey").
      whenMatched($"customers.current" === true && $"customers.loc" =!= $"staging_updates.loc").
      updateExpr(Map("end_dt" -> "staging_updates.start_dt", "current" -> "false")).
      whenNotMatched().
      insertExpr(Map(
        "id" -> "staging_updates.id",
        "name" -> "staging_updates.name",
        "loc" -> "staging_updates.loc",
        "start_dt" -> "staging_updates.start_dt",
        "end_dt" -> "null",
        "current" -> "true"
      )).execute()

    dlt.toDF.show
    dlt.toDF.printSchema()




  }

}
