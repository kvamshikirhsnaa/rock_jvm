package spark_palyground_new

import org.apache.log4j.{Logger,Level}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.elasticsearch.spark.sql._

case class AlbumIndex(artist:String, yearOfRelease:Int, albumName: String)

object SparkToES {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession.builder().
      master("local").appName("sample").
      config("spark.es.nodes", "localhost").
      config("spark.es.port", "9200").
      getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val indexDocuments = Seq(
      AlbumIndex("Led Zeppelin",1969,"Led Zeppelin"),
      AlbumIndex("Boston",1976,"Boston"),
      AlbumIndex("Fleetwood Mac", 1979,"Tusk")
    ).toDF

    indexDocuments.saveToEs("demoindex/albumindex")






  }

}
