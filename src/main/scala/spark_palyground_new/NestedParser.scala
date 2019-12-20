package spark_palyground_new

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

import scala.annotation.tailrec

object NestedParser {
  def main(args: Array[String]): Unit = {

    Logger.getLogger( "org" ).setLevel( Level.ERROR )

    val spark = SparkSession.builder().
      master( "local" ).appName( "sample" ).
      getOrCreate()

    import spark.implicits._

    val df1 = spark.read.option( "multiLine", "true" ).
      option("inferSchema", "true").format( "json" ).
      load( "src\\main\\resources\\data\\dummy2.json" )

    df1.show

    val df2 =nestedParser(df1)
    df2.show




  }

  def nestedParser(df: DataFrame) = {

    val nestedCnt = df.schema.map(x => x.dataType.typeName).
      filter(x => x == "struct" || x == "array").size

    def nestedParserRec(df: DataFrame, cnt: Int): DataFrame = {
      if (cnt == 0) {
        df
      }

      else if (df.schema.map(x => x.dataType.typeName).exists(x => x == "struct") ){
        val plainCols = df.schema.map( x => (x.name, x.dataType.typeName) ).
          filter( x => x._2 != "struct" && x._2 != "array" ).map( x => x._1 )

        val structCols = df.schema.map( x => (x.name, x.dataType.typeName) ).
          filter( x => x._2 == "struct" ).map( x => x._1 )

        val nestedStructFlds = structCols.foldLeft( Seq.empty[String] ) {
          (tempSeq, curr) => {
            val names = df.schema( curr ).dataType.asInstanceOf[StructType].fieldNames.
              map( x => s"${curr}.${x}" )
            tempSeq ++ names
          }
        }

        val flds = plainCols ++ nestedStructFlds
        val cols = flds.map( x => col( x ).as( x.replace( '.', '_' ) ) )

        val dfnew = df.select( cols: _* )

        val nestedCntNew = dfnew.schema.map( x => x.dataType.typeName ).
          filter( x => x == "struct" || x == "array" ).size

        nestedParserRec( dfnew, nestedCntNew )

      }
      else {

        val arrCols = df.schema.map( x => (x.name, x.dataType.typeName) ).
          filter( x => x._2 == "array" ).map( x => x._1 )

        val dfnew2 = arrCols.foldLeft(df) {
          (tempDF, curr) => {
            if (tempDF.schema( curr ).dataType.isInstanceOf[ArrayType]) {
              tempDF.withColumn( curr, explode( col( curr ) ) )
            }
            else tempDF
          }
        }

        val nestedCntNew2 = dfnew2.schema.map( x => x.dataType.typeName ).
          filter( x => x == "struct" || x == "array" ).size

        nestedParserRec( dfnew2, nestedCntNew2 )
      }
    }
    nestedParserRec(df, nestedCnt)
  }


}
