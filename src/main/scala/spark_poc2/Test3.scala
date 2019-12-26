package spark_poc2

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

import scala.util.Random

case class S1(profile_id: Int, interaction: Int)

object Test3 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master( "local" ).appName( "sample" ).
      getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel( "ERROR" )

    val arr = Seq(1,2,3,4,5,6,7,8,9)

    val amt = 1000

    def randId = arr(Random.nextInt(arr.length))
    def randSal = amt + Random.nextInt(100)

    def randGen = S1(randId, randSal)

    val df1 = Seq.fill(100)(randGen).toDF

    df1.show

    val df2 = df1.select(monotonically_increasing_id + 1 as "post_id", $"profile_id", 'interaction)

    df2.show

/*    df2.repartition(1).write.option("header", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\posts")*/

    val spec = Window.partitionBy('profile_id)

    val df3 = df2.withColumn("max", max('interaction) over spec).
      filter('interaction === 'max)
    df3.show

    val df4 = df2.groupBy('profile_id).agg(first('post_id) as "post_id", max('interaction) as "max")
    df4.show


  }

}
