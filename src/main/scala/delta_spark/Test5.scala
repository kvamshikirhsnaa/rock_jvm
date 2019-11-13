package delta_spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._

object Test5 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName( "sample21" ).
      master( "local" ).
      getOrCreate

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._

    val df = spark.read.option( "header", "true" ).
      option( "inferSchema", "true" ).
      csv( "C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\emp" )

    df.show

    val spec = Window.partitionBy('id).orderBy('start_dt)

    val df2 = df.withColumn("rnk", row_number() over spec)

    df2.show

    val df3 = df2.select('id, 'name, 'loc, 'start_dt, lead('start_dt, 1) over spec as "end_dt", 'rnk)

    df3.show

    val spec2 = Window.partitionBy('id).orderBy('id, 'rnk.desc)

    val df4 = df3.select('id, 'name, 'loc, 'start_dt, 'end_dt, row_number() over spec2 as "rnk").
      where('rnk === 1).sort('id)

    df4.show

    df3.createOrReplaceTempView("sample")

    spark.sql("select * from sample where (id,rnk) in " +
      "(select id, max(rnk) as rnk from sample group by id) order by id").show

    val df5 = df3.sort('id, 'rnk.desc)

    df5.show

    df5.groupBy('id).agg(first("name") as "name", first("loc") as "loc",
      first("start_dt") as "start_dt", first("end_dt") as "end_dt", first("rnk") as "rnk").
      show




  }

}