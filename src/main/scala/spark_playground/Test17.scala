package spark_playground

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._

object Test17 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master( "local" ).
      appName( "sample" ).getOrCreate()

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._

    val df = spark.read.format( "parquet" ).
      load( "C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\citybike" )

    df.show
    println( df.count )

    df.createOrReplaceTempView("citybike")

    val df2 = df.cube('pickup_id, 'drop_id).count()

    df2.show

    val df3 = df2.filter('pickup_id.isNull || 'drop_id.isNull)
    df3.show
    println(df3.count())

    val df4 = df3.filter('pickup_id.isNotNull || 'drop_id.isNotNull)

    val df5 = df4.sort('count.desc)
    df5.show

    val df6 = df4.select(when('pickup_id.isNotNull, 'pickup_id) otherwise 'drop_id as "location_id",
      when('pickup_id.isNotNull, lit("pickup")) otherwise lit("drop") as "status",
      'count).sort('count.desc)

    df6.show



    }

}
