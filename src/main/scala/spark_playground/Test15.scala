package spark_playground

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

import scala.util.Random


case class T2(station_id: Int, station_name: String)

object Test15 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master( "local" ).
      appName( "sample" ).getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel( "ERROR" )

    val stations = Seq("abcBroadWay", "Akekrn", "Bewhgjr", "BroadWayjewi", "Jeweje", "NejBroadWay",
    "Lewkjeu", "Kjeiwen", "BroadWayiues", "CewrrBroadWay", "Udnbewe", "Gbewjuyf", "Penwjfd")

    val station_ids = Seq(101,102,103,104,105)

    def randStaion_name = stations(Random.nextInt(stations.size))
    def randStation_id = station_ids(Random.nextInt(station_ids.size))

    def randGenData = T2(randStation_id, randStaion_name)

    val dfnew = Seq.fill(1000)(randGenData).toDF
    dfnew.show

/*    dfnew.repartition(1).write.option("header", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\citybike_new2")*/

    dfnew.filter( 'station_name.rlike( "BroadWay" ) ).show
    dfnew.filter( 'station_name.contains( "BroadWay" ) ).show

    dfnew.select(regexp_extract('station_name, "BroadWay", 0) as "station_name").show
    dfnew.select(regexp_replace('station_name, "BroadWay", "lalalala") as "station_name").show

    dfnew.filter( 'station_name.contains( "BroadWay" ) ).
      groupBy('station_id).count.show




  }
}
