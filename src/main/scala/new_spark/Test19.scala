package new_spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.types._

object Test19 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName( "sample21" ).
      master( "local" ).
      getOrCreate

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._

    val df = spark.read.option("header", "true").
      option("inferSchema", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\events.txt")

    df.show
    df.printSchema()

    val spec = Window.partitionBy('eventtype).orderBy('time.desc)

    val df2 = df.withColumn("prev_value", lead('value, 1) over spec).
      withColumn("rnk", row_number() over spec)

    df2.show

    val df3 = df.withColumn("prev_value", lead('value, 1) over spec).
      withColumn("rnk", row_number() over spec).where('rnk === 1 && 'prev_value.isNotNull)

    df3.show

    val df4 = df.withColumn("prev_value", lead('value, 1) over spec).
      withColumn("rnk", row_number() over spec).where('rnk === 1 && 'prev_value.isNotNull).
      select('eventtype, ('value - 'prev_value) as "diff_value")

    df4.show
    df4.explain()

    val df5 = df.withColumn("rnk", row_number() over spec).where('rnk < 3).
      withColumn("prev_value", lead('value, 1) over spec).
      where('rnk === 1 && 'prev_value.isNotNull).
      select('eventtype, ('value - 'prev_value) as "diff_value")

    df5.show
    df5.explain()

  }

}
