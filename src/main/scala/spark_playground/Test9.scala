package spark_playground

import org.apache.spark.sql.SparkSession

object Test9 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master( "local" ).
      appName( "sample" ).
      getOrCreate()

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._

    val df1 = spark.read.option( "header", "true" ).format("parquet").
      load( "C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\data\\emp_new_merge2")

    df1.show
    df1.printSchema()


  }

}
