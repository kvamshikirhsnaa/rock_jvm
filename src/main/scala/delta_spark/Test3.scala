package delta_spark

import io.delta.tables._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Test3 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName( "sample21" ).
      master( "local" ).
      getOrCreate

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._

    // Versioning

    val cust_dlt = DeltaTable.forPath("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\delta_op_new")

    cust_dlt.history.show


    // to Get Latest Version Every time from delta table
    def latestVersion = {
      cust_dlt.history.toDF.select(max($"version")).collect().map(x => x.getAs[Long](0)).toList(0)
    }

     //                                      (OR)


    // cuz in history dataframe 1 rec will be latest version always so we can take head
    def latestVersion2 = {
      cust_dlt.history.toDF.head.getAs[Int](0)
    }



    val df = spark.read.format("delta").
      load("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\delta_op_new")

    df.sort('id, 'start_dt).show
    df.printSchema()

    // versionAsOf:

    val df2 = spark.read.format("delta").option("versionAsOf", 0).
      load("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\delta_op_new")

    df2.sort('id, 'start_dt).show
    df2.printSchema()

    val df3 = spark.read.format("delta").option("versionAsOf", 1).
      load("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\delta_op_new")

    df3.sort('id, 'start_dt).show
    df3.printSchema()

    val df4 = spark.read.format("delta").option("versionAsOf", 2).
      load("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\delta_op_new")

    df4.sort('id, 'start_dt).show
    df4.printSchema()

    val df5 = spark.read.format("delta").option("versionAsOf", latestVersion).
      load("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\delta_op_new")

    df5.sort('id, 'start_dt).show
    df5.printSchema()

    // timestampAsOf:

    val df6 = spark.read.format("delta").option("timestampAsOf", "2019-10-25 17:52:00").
      load("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\delta_op_new")

    df6.sort('id, 'start_dt).show
    df6.printSchema()

    val dfnew = spark.read.option("header", "true").option("inferSchema", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\emp_csv")

    dfnew.sort('id, 'start_dt).show


  }

}
