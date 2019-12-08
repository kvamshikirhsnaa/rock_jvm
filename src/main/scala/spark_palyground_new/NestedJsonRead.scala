package spark_palyground_new

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

import scala.annotation.tailrec

object NestedJsonRead {
  def main(args: Array[String]): Unit = {

      Logger.getLogger( "org" ).setLevel( Level.ERROR )

      val spark = SparkSession.builder().
        master( "local" ).appName( "sample" ).
        getOrCreate()

      import spark.implicits._

      val mongodb_host_name = "localhost"
      val mongodb_port_no = "27017"
      // val mongodb_user_name = "admin"
      // val mongodb_password = "admin"
      val mongodb_datebase_name = "test_db"
      val mogodb_collection_name = "dummy_json_data"

      val mongodb_uri = s"mongodb://${mongodb_host_name}:" +
        s"${mongodb_port_no}/${mongodb_datebase_name}.${mogodb_collection_name}"

      println("mongodb_ uri" + mongodb_uri)

      val df1 = spark.read.option( "multiLine", "true" ).
        option("inferSchema", "true").format( "json" ).
        load( "src\\main\\resources\\data\\dummy2.json" )

      df1.show
      df1.printSchema()

      val df2 = arrStrDF(df1)
      df2.show
      df2.printSchema()

/*
      df2.write.format("mongo").
        option("uri", mongodb_uri).
        option("database", mongodb_datebase_name).
        option("collection", mogodb_collection_name).
        save()

*/

    }


  def arrStrDF(df: DataFrame): DataFrame = {
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
