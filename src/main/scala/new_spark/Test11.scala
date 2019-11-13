package new_spark

import org.apache.spark.sql.SparkSession

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.sql.Timestamp
import org.apache.spark.sql.types._
import org.apache.spark.sql.catalyst.encoders.RowEncoder
import scala.collection.mutable.ListBuffer


case class ClickStream(user_id: Long, datetime: Timestamp, os: String, browser: String, response_time_ms: Long, url: String)


object Test11 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val df = spark.read.option("inferSchema", "true").
      json("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\clickStream.json")

    df.show
    df.printSchema()


    // approach2:

    val format = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss")

    val rdd = spark.sparkContext.
      textFile("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\clickStream.json")

    val parsedRDD  = rdd.map(x =>
    {
      val obj = new JSONObject(x)
      ClickStream(obj.getLong("user_id"), new Timestamp(format.parse(obj.getString("datetime")).getTime),
        obj.getString("os"), obj.getString("browser"), obj.getLong("response_time_ms"),
        obj.getString("url"))
    }
    )

    val df2 = parsedRDD.toDF

    df2.show
    df2.printSchema()

    // approach3: using Encoders

/*    val groupByColumns = List(("os","string"),("browser","string"))
    val colToAvg = ("response_time_ms", "integer")

    val DF3 = spark.read.
      text("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\clickStream.json")

    var schema = new StructType

    for (i <- (groupByColumns ++ List(colToAvg)))
      schema = schema.add(i._1, i._2)

    val encoder = RowEncoder.apply(schema)

    val DF4 = DF3.map( x =>
    {
      val obj = new JSONObject(x.getString(0))

      var buffer = new ListBuffer[Object]()
      for (i <- (groupByColumns ++ List(colToAvg)))
        buffer += obj.get(i._1)

      org.apache.spark.sql.Row(buffer.toList:_*)
    })

    DF4.show()*/

  }

}
