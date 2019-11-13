package myprac.spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._

import scala.util.Random
import scala.math.BigDecimal


case class S1(id: Int, state: String)

case class S2(id: Int, state: String, city: String)

object Test3 {
  def main(args: Array[String]): Unit = {


    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    spark.conf.set("spark.sql.autoBroadcastJoinThreshold", -1)

    import spark.implicits._

    val arr = Seq(S1(1, "Telangana"),
      S1(2, "Karnataka"),
      S1(3, "Andhrapradesh"),
      S1(4, "Tamilnadu"),
      S1(5, "Kerala"),
      S1(6, "Maharashtra"),
      S1(7, "Delhi"),
      S1(8, "Uttarapradesh"),
      S1(9, "Rajasthan"),
      S1(10, "Gujarath"),
      S1(11, "Goa")
    )

    def randomState(): S1 = {
      val ind = if (Random.nextBoolean()) 0 else Random.nextInt(arr.size)
      arr(ind)
    }

    def randomCity(): S2 = {
      val pair = randomState()
      val city = s"${Random.alphanumeric.take(5).mkString("")}"
      S2(pair.id, pair.state, city)
    }

    val df1 = Seq.fill(1000)(randomState()).toDS

    val df2 = Seq.fill(1000)(randomCity()).toDS

    df1.show

    df2.show

  }

}
