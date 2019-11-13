package myprac.spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import io.delta.tables._

import scala.util.Random
import scala.math.BigDecimal

object Test8 {
  def main(args: Array[String]): Unit = {


    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val data = spark.range(0, 5)

//    data.write.format("delta").
//      save("C:\\Users\\Kenche.vamshikrishna\\Desktop\\Spark\\Delta\\sample")

    val df = spark.read.format("delta").
      load("C:\\Users\\Kenche.vamshikrishna\\Desktop\\Spark\\Delta\\sample")

    df.show()

//  updating

    val data2 = spark.range(5, 10)

//    data2.write.format("delta").
//      mode("overwrite").
//      save("C:\\Users\\Kenche.vamshikrishna\\Desktop\\Spark\\Delta\\sample")
//
//    df.show()

    val deltaTable = DeltaTable.
      forPath("C:\\Users\\Kenche.vamshikrishna\\Desktop\\Spark\\Delta\\sample")

// Update every even value by adding 100 to it
//    deltaTable.update(expr("id % 2 == 0"), Map("id" -> expr("id + 100")))
//    deltaTable.update($"id" % 2 === 0, Map("id" -> ($"id" + 100))) // Map[String, Column]

//    df.show

//  Delete every even value
//    deltaTable.delete($"id" % 2 === 0)
//
//    df.show

// Upsert (merge) new data

    val newData = spark.range(0, 20).as("newData").toDF



/*    deltaTable.as("oldData").merge(newData, "oldData.id = newData.id").
      whenMatched.update(Map("id" -> $"newData.id")).
      whenNotMatched.insert(Map("id" -> $"newData.id")).execute()

   df.show*/

//    deltaTable.toDF.show
//
    val df3 = spark.read.format("delta").option("versionAsOf", 0).
      load("C:\\Users\\Kenche.vamshikrishna\\Desktop\\Spark\\Delta\\sample")

    df3.show()

    val streamingDf = spark.readStream.format("rate").load()

    val stream = streamingDf.select($"value" as "id").writeStream.
      format("delta").
      option("checkpointLocation","C:\\Users\\Kenche.vamshikrishna\\Desktop\\Spark\\Delta\\tmp").
      start("C:\\Users\\Kenche.vamshikrishna\\Desktop\\Spark\\Delta\\sample")

    stream.stop()

    df.show




  }

}
