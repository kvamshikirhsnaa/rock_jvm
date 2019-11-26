package spark_palyground_new

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import com.databricks.spark.xml._
import org.apache.spark.sql.functions.{col, explode}
import org.apache.spark.sql.types._

object NestedXMLRead2 {
  def main(args: Array[String]): Unit = {

    Logger.getLogger( "org" ).setLevel( Level.ERROR )

    val spark = SparkSession.builder().
      master( "local" ).appName( "sample" ).
      getOrCreate()

    import spark.implicits._

    val df1 = spark.read.option( "rowTag", "CourseOffering" ).
      option( "inferSchema", "true" ).format( "xml" ).
      load( "src\\main\\resources\\data\\dummy2.xml" )

    df1.show( false )
    df1.printSchema()

    var df2 = df1
    val cols = df2.schema.map( x => (x.name, x.dataType) ).map( x => (x._1, x._2.typeName) ).
      filter( x => (x._2 == "struct") || (x._2 == "array") ).
      map( x => x._1 )

    println( cols )

    while (df2.schema.map(x => x.dataType).map(x => x.typeName).exists(x => (x == "struct") || (x == "array"))){
      df2 = arrstrDF(df2, cols)
    }

    df2.show

    val df3 = arrstrDF2(df1)
    df3.show

  }

    def arrstrDF(df: DataFrame, arrstr: Seq[String]): DataFrame = arrstr.foldLeft( df ) {
      (tempDF, curr) => {
        if (tempDF.schema( curr ).dataType.isInstanceOf[ArrayType]) {
          tempDF.withColumn( curr, explode( col( curr ) ) )
        }
        else {
          val nestCols = tempDF.schema( curr ).dataType.asInstanceOf[StructType].fields.map( x => x.name )
          val dfnew = nestCols.foldLeft( tempDF ) {
            (tdf, fld) => {
              tdf.withColumn( s"${curr}_${fld}", col( s"${curr}.${fld}" ) )
            }
          }
          dfnew.drop( curr )
        }
      }
    }


  def arrstrDF2(df: DataFrame) = {
    val cols = df.schema.map( x => (x.name, x.dataType) ).map( x => (x._1, x._2.typeName) ).
      filter( x => (x._2 == "struct") || (x._2 == "array") ).
      map( x => x._1 )

    def arrStrDFRec(df: DataFrame, cols: Seq[String]): DataFrame = {
      if (df.schema.map(x => x.dataType).map(x => x.typeName).forall(x => (x != "struct") && (x != "array"))){
        df
      }
      else {
        val df2 = cols.foldLeft(df) {
          (tempDF, curr) => {
            if (tempDF.schema( curr ).dataType.isInstanceOf[ArrayType]) {
              tempDF.withColumn( curr, explode( col( curr ) ) )
            }
            else {
              val nestCols = tempDF.schema( curr ).dataType.asInstanceOf[StructType].fields.map( x => x.name )
              val dfnew = nestCols.foldLeft( tempDF ) {
                (tdf, fld) => {
                  tdf.withColumn( s"${curr}_${fld}", col( s"${curr}.${fld}" ) )
                }
              }
              dfnew.drop( curr )
            }
          }
        }
        val arr = df2.schema.map( x => (x.name, x.dataType) ).map( x => (x._1, x._2.typeName) ).
          filter( x => (x._2 == "struct") || (x._2 == "array") ).
          map( x => x._1 )
        arrStrDFRec(df2, arr)
      }
    }
    arrStrDFRec(df, cols)
  }



}
