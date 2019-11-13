package new_spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import scala.util.Try


case class Ele(id: String, names: String, index: Boolean)

case class Rec(
                A: Map[String, Array[Ele]],
                key: String
              )


object Test14 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName( "sample21" ).
      master( "local" ).
      getOrCreate

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._

    val a1 = Ele("1", "0.01", true)
    val a2 = Ele("2", "0.02", false)
    val a3 = Ele("3", "0.02", false)
    val a4 = Ele("4", "0.04", true)
    val a5 = Ele("5", "0.05", true)
    val a6 = Ele("6", "0.06", false)

    val rec1 = Rec(Map("innerkey1" -> Array(a1, a2)), "1")
    val rec2 = Rec(Map("innerkey2" -> Array(a2, a3)), "2")
    val rec3 = Rec(Map("innerkey3" -> Array(a3, a4)), "3")
    val rec4 = Rec(Map("innerkey4" -> Array(a4, a5)), "4")
    val rec5 = Rec(Map("innerkey5" -> Array(a5, a6)), "5")
    val rec6 = Rec(Map("innerkey6" -> Array(a2, a6)), "6")

    val df = spark.sparkContext.parallelize(Seq(rec1, rec2, rec3, rec4, rec5, rec6)).toDF("A", "key")

    df.show
    df.printSchema()

//    val df2 = df.select(explode($"A") as Seq("id", "value"), $"key")
//
//    df2.show
//    df2.printSchema()
//
//    val df3 = df2.select($"id", explode($"value") as "newvalue", $"key")
//
//    df3.show
//    df3.printSchema()
//
//    val df4 = df3.filter($"newvalue.id" === $"key")
//
//    df4.show

    val df2 = df.select(explode($"A") as Seq("id", "value"), $"key").
      select($"id", explode($"value") as "newvalue", $"key").
      filter($"newvalue.id" === $"key")

    df2.show
    df2.printSchema()








  }
}