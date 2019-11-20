package spark_playground

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.log4j.{Level, Logger}

case class Purchase(id: Int, amt: Int)

import scala.util.Random

object SkewJoinNew {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession.builder().
      master( "local" ).
      appName( "sample" ).getOrCreate()

    spark.conf.set( "spark.sql.autoBroadcastJoinThreshold", -1 )

    import spark.implicits._


    val ids = Seq( 101, 102, 103, 104, 105, 106, 107, 108, 109, 110 )

    def randPurchase = 1000 + Random.nextInt( 100 )

    def randGen = {
      if (Random.nextBoolean()) Purchase( ids( 0 ), randPurchase )
      else Purchase( ids( Random.nextInt( ids.size ) ), randPurchase )
    }

    val df1 = Seq.fill( 1000 )( randGen ).toDF

    df1.show

    val df2 = Seq( (101, "saikrishna"), (102, "aravind"), (103, "narahari"), (104, "prakash"), (105, "anji"), (106, "govind"),
      (107, "anji"), (108, "nani"), (109, "tilak"), (110, "venkat") ).toDF( "id", "name" )

    df2.show

    val joindf = df1.join( df2, Seq( "id" ) )

    println( joindf.rdd.partitions.size )
    println( joindf.rdd.mapPartitions( x => Iterator( x.size ) ).collect.toList )


    val df1new = df1.withColumn("id",
      when('id === 101, concat_ws("_", 'id, floor(rand(123456) * 9)))
      otherwise 'id)

    df1new.show

    val arr = array(lit("101_1"), lit("101_2"), lit("101_3"), lit("101_4"), lit("101_5")
      , lit("101_6"), lit("101_7"), lit("101_8"), lit("101_9"), lit("101_10"))

    val df2new = df2.withColumn("id",
      when('id === 101, arr) otherwise split('id, ","))

    df2new.show

    val df3new = df2new.select(explode('id) as "id", 'name)

    df3new.show

    val joindfnew = df1new.join(df3new, Seq("id"))
    joindfnew.show(100)

    println( joindfnew.rdd.partitions.size )
    println( joindfnew.rdd.mapPartitions( x => Iterator( x.size ) ).collect.toList )


    val joinDfNewRes = joindfnew.withColumn("id", regexp_extract('id, "[0-9]+", 0))
    joinDfNewRes.show

    println( joinDfNewRes.rdd.partitions.size )
    println( joinDfNewRes.rdd.mapPartitions( x => Iterator( x.size ) ).collect.toList )



  }
}
