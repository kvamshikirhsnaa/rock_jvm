package spark_playground

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._
import io.delta.tables._

object Test7 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master( "local" ).
      appName( "sample" ).getOrCreate()

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._

    val df1 = spark.read.option( "header", "true" ).
      option( "inferSchema", "true" ).
      csv( "C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\data\\emp1.txt")

    df1.show
    df1.printSchema()

    val df2 = spark.read.option( "header", "true" ).
      option( "inferSchema", "true" ).
      csv( "C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\data\\emp2.txt" )

    df2.show
    df2.printSchema()


    val unionDF = df1.select('id, 'name, 'age, 'sal).union(df2.select('id, 'name, 'age, 'sal))

    unionDF.show

    val combineDF = unionDF.join(df1, Seq("id", "name", "age", "sal"), "leftouter").
      join(df2, Seq("id", "name", "age", "sal"), "leftouter")

    combineDF.show
    combineDF.printSchema()

  }

}
