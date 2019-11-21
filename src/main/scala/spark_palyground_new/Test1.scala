package spark_palyground_new
import org.apache.spark.sql.SparkSession
import org.apache.log4j.{Level, Logger}

import scala.util.Random

case class Purchase(id: Int, amt: Int)

object Test1 {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession.builder().
      master("local").appName("sample").
      getOrCreate()

    spark.conf.set("spark.sql.autoBroadcastJoinThreshold", -1)

    import spark.implicits._

    val data = Seq(1,2,3,4,5,6,7,8,9,10)

    val prices = Seq(1000,2000,3000,4000,5000,10000)

    def randId = data(Random.nextInt(data.length))
    def randAmt = prices(Random.nextInt(prices.length)) + Random.nextInt(500)

    def randPurchase = Purchase(randId, randAmt)

    val df1 = Seq((1,"saikrishna"), (2, "aravind"), (3, "narahari"), (4, "prakash"), (5, "anji"), (6, "govind"),
      (7,"anji"),(8,"nani"),(9,"tilak"),(10,"venkat")).toDF("id", "name")

    val df2 = Seq.fill(1000)(randPurchase).toDF

    df1.show
    df2.show

    val joinDF1 = df1.join(df2, Seq("id"))
    joinDF1.show
    println(joinDF1.explain)


    val joinDF2 = df1.join(df2.filter('id > 5), Seq("id"))
    joinDF2.show
    println(joinDF2.explain)


    val joinDF3 = df1.filter('id > 5).join(df2, Seq("id"))
    joinDF3.show
    println(joinDF3.explain)


    val joinDF4 = df1.join(df2.filter('amt > 5000), Seq("id"))
    joinDF4.show
    println(joinDF4.explain)


    val joinDF5 = df1.filter('id > 5).join(df2.filter('amt > 5000), Seq("id"))
    joinDF5.show
    println(joinDF5.explain)


    val joinDF6 = df1.filter('name.startsWith("s") || 'name.endsWith("d") || 'name.endsWith("i") ).
      join(df2.filter('amt > 5000), Seq("id"))

    joinDF6.show
    println(joinDF6.explain)





  }

}
