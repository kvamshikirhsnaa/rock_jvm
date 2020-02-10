package spark_playground

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._
import org.apache.log4j.{Level, Logger}

object Test4 {
  def main(args: Array[String]): Unit = {

    Logger.getLogger( "org" ).setLevel( Level.ERROR )

    val spark = SparkSession.builder().
      master( "local" ).
      appName( "sample" ).getOrCreate()

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._

    val df1 = spark.read.option( "header", "true" ).
      option( "inferSchema", "true" ).
      csv( "C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\data\\event1.txt" )

    df1.show
    df1.printSchema()


    val df2 = spark.read.option( "header", "true" ).
      option( "inferSchema", "true" ).
      csv( "C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\data\\event2.txt" )

    df2.show
    df2.printSchema()

    val unionDateDF = df1.select('dt).union(df2.select('dt)).dropDuplicates()

    unionDateDF.show

    unionDateDF.join(df1, Seq("dt"), "leftouter").join(df2, Seq("dt"), "leftouter").show




  }
}