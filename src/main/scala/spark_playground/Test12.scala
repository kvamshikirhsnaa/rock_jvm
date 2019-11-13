package spark_playground

import org.apache.spark.sql.SparkSession

import scala.util.Random

case class T1(pickup_id: Int, drop_id: Int)

object Test12 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master( "local" ).
      appName( "sample" ).getOrCreate()

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._

    val data = Seq(101,102,103,104,105,106,107,108,109,110)

    def randPick = data(Random.nextInt(data.length))
    def randDrop = data(Random.nextInt(data.length))

    def newdata = T1(randPick, randDrop)

    val df = Seq.fill(10000)(newdata).toDF("pickup_id", "drop_id")

    val df2 = df.filter('pickup_id =!= 'drop_id)

    println(df2.count)
    df2.show

 /*   df2.repartition(1).write.
      save("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\citybike")*/


    df2.repartition(1).write.option("header", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\citybike_new")


  }

}
