package delta_spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import io.delta.tables._


object Test2 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName( "sample21" ).
      master( "local" ).
      getOrCreate

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._

    val df = spark.read.option("header", "true").
      option("inferSchema", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\emp4.txt")

    df.show

/*

    val df2 = df.withColumn("end_dt", lit(null).cast(TimestampType)).
      withColumn("current", lit("true"))

    df2.show
    df2.printSchema

    df2.write.format("delta").mode("append").
      save("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\delta_op")

*/

    // Rows to INSERT new loc of existing customers
    val cust_dlt = DeltaTable.forPath("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\delta_op_new")

    cust_dlt.toDF.show()

    val newLocToInsert = df.as("updates").join(cust_dlt.toDF.as("customers"), "id").
      where("customers.current = true and  updates.loc <> customers.loc")

    newLocToInsert.show()

        // Stage the update by unioning two sets of rows
        // 1. Rows that will be inserted in the `whenNotMatched` clause
        // 2. Rows that will either UPDATE the current addresses of existing customers or
        // INSERT the new addresses of new customers

    val stagedUpdates = newLocToInsert.
      selectExpr("NULL as mergekey", "updates.*").
      union(df.selectExpr("id as mergekey", "*"))

    stagedUpdates.show

    // Apply SCD Type 2 operation using merge

    cust_dlt.as("customers").
      merge(stagedUpdates.as("staged_updates"), "customers.id = mergekey").
      whenMatched("customers.current = true and customers.loc <> staged_updates.loc").
      updateExpr(Map("current" -> "false", "end_dt" -> "staged_updates.start_dt")).
      whenNotMatched().
      insertExpr(Map(
        "id" -> "staged_updates.id",
        "name" -> "staged_updates.name",
        "loc" -> "staged_updates.loc",
        "start_dt" -> "staged_updates.start_dt",
        "end_dt" -> "null",
        "current" -> "true"
      )).execute()

    cust_dlt.toDF.show()







  }

}
