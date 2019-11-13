package delta_spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions._
import io.delta.tables._


case class Data(key: String, value: String)

// Changes in Data defined as follows
// newValue = new value for key, or null if the key was deleted
// deleted = whether the key was deleted
// time = timestamp needed for ordering the changes, useful collapsing multiple changes to the same key by finding the latest effective change
case class ChangeData(key: String, newValue: String, deleted: Boolean, time: Long) {
  assert(newValue != null ^ deleted)
}


object Test7 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName( "sample21" ).
      master( "local" ).
      getOrCreate

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._

/*    val target = Seq(
      Data("a", "0"),
      Data("b", "1"),
      Data("c", "2"),
      Data("d", "3") ).toDF().write.format("delta").mode("overwrite").
      save("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\cdc_delta")*/


//    Change Data:
//
//    This table contains all the changes that need to applied. Note that the same key
//    may have been changed multiple times. Multiple changes are ordered by their timestamp.

    val deltaTable = DeltaTable.forPath("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\cdc_delta")

    deltaTable.toDF.show


    val changeDataSource = Seq(
      ChangeData("a", "10", deleted = false, time = 0),
      ChangeData("a", null, deleted = true, time = 1),   // a was updated and then deleted

      ChangeData("b", null, deleted = true, time = 2),   // b was just deleted once

      ChangeData("c", null, deleted = true, time = 3),   // c was deleted and then updated twice
      ChangeData("c", "20", deleted = false, time = 4),
      ChangeData("c", "200", deleted = false, time = 5)
    ).toDF().createOrReplaceTempView("changes")



    // DataFrame with changes having following columns
    // - key: key of the change
    // - time: time of change for ordering between changes (can replaced by other ordering id)
    // - newValue: updated or inserted value if key was not deleted
    // - deleted: true if the key was deleted, false if the key was inserted or updated
    val changesDF = spark.table("changes")


    // Find the latest change for each key based on the timestamp
    // Note: For nested structs, max on struct is computed as
    // max on first struct field, if equal fall back to second fields, and so on.
    val latestChangeForEachKey = changesDF
      .selectExpr("key", "struct(time, newValue, deleted) as otherCols" )
      .groupBy("key")
      .agg(max("otherCols").as("latest"))
      .selectExpr("key", "latest.*")

    latestChangeForEachKey.show() // shows the latest change for each key

    deltaTable.as("t")
      .merge(
        latestChangeForEachKey.as("s"),
        "s.key = t.key")
      .whenMatched("s.deleted = true")
      .delete()
      .whenMatched()
      .updateExpr(Map("key" -> "s.key", "value" -> "s.newValue"))
      .whenNotMatched("s.deleted = false")
      .insertExpr(Map("key" -> "s.key", "value" -> "s.newValue"))
      .execute()


    deltaTable.toDF.show


  }

}
