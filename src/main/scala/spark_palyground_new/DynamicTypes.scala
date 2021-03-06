package spark_palyground_new

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

import scala.annotation.tailrec

// dynamically handling struct and array types

object DynamicTypes {
  def main(args: Array[String]): Unit = {

    Logger.getLogger( "org" ).setLevel( Level.ERROR )

    val spark = SparkSession.builder().
      master( "local" ).appName( "sample" ).
      getOrCreate()

    import spark.implicits._

    val df1 = spark.read.option( "multiLine", "true" ).
      option("inferSchema", "true").format( "json" ).
      load( "src\\main\\resources\\data\\dummy2.json" )


    var df2 = df1

    df1.show( false )
    println( df1.schema )
    df1.printSchema()

    var nested_column_count = 1

    while (nested_column_count != 0) {

      if (df2.schema.map( x => x.dataType ).map( x => x.typeName ).contains( "array" )) {
        val arr = df2.schema.map( x => (x.name, x.dataType) ).filter( x => x._2.typeName == "array" ).map( x => x._1 )
        df2 = arrayDF( df2, arr )
      }

      if (df2.schema.map( x => x.dataType ).map( x => x.typeName ).contains( "struct" )) {
        val str = df2.schema.map( x => (x.name, x.dataType) ).filter( x => x._2.typeName == "struct" ).map( x => x._1 )
        df2 = structDF( df2, str )
      }

      if (df2.schema.map(x => x.dataType).map(x => x.typeName).forall(x => (x != "struct") && (x != "array"))) {
        nested_column_count = 0
      }
    }

    df2.show
    df2.printSchema()

    val df3 = arrStrDF2(df1)
    df3.show


  }

  def structDF(df: DataFrame, str: Seq[String]): DataFrame = str.foldLeft(df){
    (tempDF, curr) => {
      val nestCols = tempDF.schema(curr).dataType.asInstanceOf[StructType].fields.map(x => x.name)
      val dfnew = nestCols.foldLeft(tempDF) {
        (tdf, fld) => {
          tdf.withColumn(s"${curr}_${fld}", col(s"${curr}.${fld}"))
        }
      }
      dfnew.drop(curr)
    }
  }

  def arrayDF(df: DataFrame, arr: Seq[String]): DataFrame = arr.foldLeft(df){
    (tempDF, curr) => {
      tempDF.withColumn(curr, explode(col(curr)))
    }
  }

  def arrStrDF2(df: DataFrame): DataFrame = {
    val cols = df.schema.map( x => (x.name, x.dataType.typeName) ).
      filter( x => (x._2 == "struct") || (x._2 == "array") ).map( x => x._1 )

    @tailrec
    def arrStrDFRec(df: DataFrame, cols: Seq[String]): DataFrame = {
      if (df.schema.map(x => x.dataType.typeName).forall(x => (x != "struct") && (x != "array"))){
        df
      }
      else {
        val df2 = cols.foldLeft(df) {
          (tempDF, curr) => {
            if (tempDF.schema( curr ).dataType.isInstanceOf[ArrayType]) {
              tempDF.withColumn( curr, explode( col( curr ) ) )
            }
            else {
              val nestCols = tempDF.schema( curr ).dataType.asInstanceOf[StructType].fieldNames
              val dfnew = nestCols.foldLeft( tempDF ) {
                (tdf, fld) => {
                  tdf.withColumn( s"${curr}_${fld}", col( s"${curr}.${fld}" ) )
                }
              }
              dfnew.drop( curr )
            }
          }
        }
        val arr = df2.schema.map( x => (x.name, x.dataType.typeName) ).
          filter( x => (x._2 == "struct") || (x._2 == "array") ).map( x => x._1 )

        arrStrDFRec(df2, arr)
      }
    }

    arrStrDFRec(df, cols)
  }





}