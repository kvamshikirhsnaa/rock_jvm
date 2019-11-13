package spark_playground

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{lit, when}

object Test14 {
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

    val df2 = df.groupBy( 'pickup_id ).count
    df2.show()

    val df3 = df.groupBy('drop_id).count
    df3.show

    // finding top 3 busy traffic stations either pickup or drops happens.

    val df6 = df2.select('pickup_id, lit(null) as "drop_id", 'count)

    val df7 = df3.select(lit(null) as "pickup_id", 'drop_id, 'count)

    val df8 = df6.union(df7)

    df8.show

    val df9 = df8.select(when('pickup_id.isNotNull, 'pickup_id) otherwise 'drop_id as "location",
      when('pickup_id.isNotNull, lit("pickup")) otherwise lit("drop") as "status", 'count).
      sort('count.desc)

    df9.show



  }

}
