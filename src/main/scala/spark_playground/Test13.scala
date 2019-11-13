package spark_playground

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Test13 {
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


    // finding top 3 busy traffic stations for pickup and drops

    val df4 = df2.join(df3, 'pickup_id === 'drop_id, "inner")
    df4.show

    val df5 = df4.select('pickup_id as "location", df2("count") + df3("count") as "count").
      sort('count.desc)

    df5.show(3)
    df5.show

    val df6 = df2.join(df3, 'pickup_id === 'drop_id, "leftouter")
    df6.show

    val df7 = df4.select('pickup_id as "location", df2("count") + df3("count") as "count").
      sort('count.desc)

    df7.show(3)
    df7.show









  }

}
