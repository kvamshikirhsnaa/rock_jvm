package new_spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._


case class Status(id: Int, delay: Int, p1: String, p2: String, p3: String, p4: String)

object Test18 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName( "sample21" ).
      master( "local" ).
      getOrCreate

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._

    val s1 = Status(1, 3, "a", "b", "c", "d")
    val s2 = Status(2, 1, "m", "n", "o", "p")
    val s3 = Status(3, 2, "q", "r", "s", "t")
    val s4 = Status(4, 0, "g", "h", "i", "j")
    val s5 = Status(5, 4, "u", "v", "w", "x")

    val df = Seq(s1, s2, s3, s4, s5).toDF
    df.show

    val index = $"delay" - 1

    val df2 = df.withColumn("delayed", array($"p1", $"p2", $"p3", $"p4")(index))
    df2.show




  }

}
