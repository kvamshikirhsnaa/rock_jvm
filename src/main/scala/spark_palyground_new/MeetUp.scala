package spark_palyground_new

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

object MeetUp {
  def main(args: Array[String]): Unit = {

    Logger.getLogger( "org" ).setLevel( Level.ERROR )

    val spark = SparkSession.builder().
      master( "local" ).appName( "sample" ).
      getOrCreate()

    import spark.implicits._

    val df1 = spark.read.option( "multiLine", "true" ).
      option( "inferSchema", "true" ).format( "json" ).
      load( "src\\main\\resources\\data\\meetupnew.json" )

    df1.show(false)
    df1.printSchema()

    val df2 = arrStrDF(df1)
    df2.show
    df2.printSchema()


  }


  def arrStrDF(df: DataFrame) = {
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
