package spark_playground

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._

object Test16 {
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

    val df2 = df.groupBy('pickup_id).count
    val df3 = df.groupBy('drop_id).count

    val df4 = df2.union(df3).groupBy('pickup_id).
      agg(sum('count) as "count").sort('count.desc)

    df4.show

  }

}
