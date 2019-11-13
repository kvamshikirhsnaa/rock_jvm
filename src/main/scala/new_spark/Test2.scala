package new_spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._

import scala.util.Random
import scala.math.BigDecimal


object Test2 {
  def main(args: Array[String]): Unit = {


    val spark = SparkSession.builder().
      appName( "sample21" ).
      master( "local" ).
      getOrCreate

    spark.sparkContext.setLogLevel( "ERROR" )

   // spark.conf.set( "spark.sql.autoBroadcastJoinThreshold", -1 )

    import spark.implicits._

    val df1 = spark.read.option( "header", "true" ).
      option( "inferSchema", "true" ).
      csv( "C:\\Users\\Kenche.vamshikrishna\\Desktop\\Spark\\emp" )

    val df2 = spark.read.option( "header", "true" ).
      option( "inferSchema", "true" ).
      csv( "C:\\Users\\Kenche.vamshikrishna\\Desktop\\Spark\\dept" )

    df1.show

    df2.show

    val joinDF = df1.join(df2, Seq("dno"), "leftsemi")

    joinDF.show

    println(joinDF.explain())

    df1.groupBy($"dno", $"name").count().show


  }

}
