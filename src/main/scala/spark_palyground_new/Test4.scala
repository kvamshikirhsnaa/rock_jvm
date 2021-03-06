package spark_palyground_new

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.Column

object Test4 {
  def main(args: Array[String]): Unit = {

    Logger.getLogger( "org" ).setLevel( Level.ERROR )

    val spark = SparkSession.builder().
      master( "local" ).appName( "sample" ).
      getOrCreate()

    import spark.implicits._

    val df = Seq((1, "Saikrishna", "Hyderabad", "BE", 24), (2, "Aravind", "Mahabubnagar", "BAMS", 25),
      (3, "Prakash", "Kotakonda", "B.Tech", 26)).toDF("id", "name", "loc", "qual", "age")
    df.show

    var df2 = df.select(struct('id, 'name) as "names", struct('loc, 'qual, 'age) as "info")
    df2.show

    val arr = df2.schema.names

    arr.foreach {x =>
      val nestCols = df2.schema(x).dataType.asInstanceOf[StructType].fields.map(x => x.name)
      for (fds <- nestCols) {
        df2 = df2.withColumn(fds, col(s"${x}.${fds}"))
      }
    }

    df2.show

    val df3 = df2.drop(arr:_*)

    df3.show


    // Using foldLeft

    println("Using foldLeft:")

    val df4 = df.select(struct('id, 'name) as "names", struct('loc, 'qual, 'age) as "info")
    df4.show

    val arr2 = df4.schema.names

    val df5 = arr2.foldLeft(df4) {
      (tempDF, curr) => {
        val nestCols = df4.schema(curr).dataType.asInstanceOf[StructType].fields.map(x => x.name)
        val dfnew = nestCols.foldLeft(tempDF) {
          (tdf, fld) => {
            tdf.withColumn(fld, col(s"${curr}.${fld}"))
          }
        }
        dfnew.drop(curr)
      }
    }

    df5.show


  }

}
