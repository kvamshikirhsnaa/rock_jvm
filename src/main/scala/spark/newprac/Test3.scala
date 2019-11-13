package spark.newprac

import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.storage.StorageLevel._
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random



case class Test(a: Int = Random.nextInt(1000000),
                b: Double = Random.nextDouble,
                c: String = Random.nextString(1000),
                d: Seq[Int] = (1 to 100).map(_ => Random.nextInt(1000000))) extends Serializable




object Test3 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    import spark.implicits._
    spark.sparkContext.setLogLevel("ERROR")

    val input = spark.sparkContext.parallelize(1 to 10000, 15).map(_ => Test()).persist(DISK_ONLY)

    val time1 = System.currentTimeMillis()
    println(time1)
    println(input.count())               // Force initialization
    println(System.currentTimeMillis() - time1)

    val shuffled = input.repartition(20)
    val time2 = System.currentTimeMillis()

    println(time2)
    println(shuffled.count)
    println(System.currentTimeMillis() - time2)


/*
    val input2 = spark.read.
      option("inferSchema", "true").option("header", "false").
      csv("C:\\Users\\Kenche.vamshikrishna\\Desktop\\test.txt").toDF("id","state","date")



    val df = input2.select('id).where('id === 1)
    val ds = input2.as[Test2].map(x => x.id).filter(x => x == 1)

    df.show
    ds.show
    println(ds.getClass.getTypeName)
*/


    def input3(i: Int) = spark.sparkContext.parallelize(1 to i*10000000)

    def serial = (1 to 10).map(i => input3(i).reduce(_ + _)).reduce(_ + _)
    def parallel = (1 to 10).map(i => Future(input3(i).reduce(_ + _))).map(Await.result(_, 10.minutes)).reduce(_ + _)

    println(serial)
    println(parallel)


  }
  case class Test2(id: Int, state: String, date: Int)

}
