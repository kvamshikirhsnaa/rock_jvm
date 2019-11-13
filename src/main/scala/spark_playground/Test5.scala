package spark_playground

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._
import io.delta.tables._

object Test5 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master( "local" ).
      appName( "sample" ).getOrCreate()

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._

    val df1 = spark.read.option( "header", "true" ).
      option( "inferSchema", "true" ).
      csv( "C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\data\\emp1.txt" )

    df1.show
    df1.printSchema()

/*

    df1.repartition(1).write.format("delta").
      save("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\data\\emp_merge")

*/

    val df2 = spark.read.option( "header", "true" ).
      option( "inferSchema", "true" ).
      csv( "C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\data\\emp2.txt" )


    df2.show
    df2.printSchema()

      df2.repartition(1).write.format("delta").
      mode("append").option("mergeSchema", "true").
      save("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\data\\emp_merge")


    val dt = DeltaTable.forPath("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\data\\emp_merge")

    dt.toDF.show
    dt.toDF.printSchema()

  }

}
