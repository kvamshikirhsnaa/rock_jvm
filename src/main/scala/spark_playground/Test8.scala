package spark_playground

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._
import io.delta.tables._

object Test8 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master( "local" ).
      appName( "sample" ).getOrCreate()

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._

    val df1 = spark.read.option( "header", "true" ).
      option( "inferSchema", "true" ).
      csv( "C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\data\\employee1.txt")

    df1.show
    df1.printSchema()

    val df2 = spark.read.option( "header", "true" ).
      option( "inferSchema", "true" ).
      csv( "C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\data\\employee2.txt" )

    df2.show
    df2.printSchema()


    val joinedDF = df1.join(df2, Seq("id"), "fullouter")

    joinedDF.show

    val df1cols = df1.columns
    val df2cols = df2.columns

    val commoncols = df1cols.intersect(df2cols).tail
    commoncols.foreach(x => print(x + " "))

    val df3 = commoncols.foldLeft(joinedDF){
      (tempDF, column) => {
        tempDF.withColumn(s"${column}_new", concat_ws(",", df1(column), df2(column))).
          drop(column)
      }
    }

    df3.show
    df3.printSchema()

    val arr = df3.columns.filter(x => x.contains("new"))
    arr.foreach(x => print(x + " "))

    val df4 = arr.foldLeft(df3) {
      (tempDF, column) => {
        tempDF.withColumn(s"${column.take(column.indexOf("_"))}"  , col(column)).
          drop(column)
      }
    }

    df4.show
    df4.printSchema()

/*    df4.write.
      save("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\data\\emp_new_merge2")*/

  }

}

