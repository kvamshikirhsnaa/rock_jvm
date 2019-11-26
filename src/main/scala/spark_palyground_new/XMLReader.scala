package spark_palyground_new

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import com.databricks.spark.xml._
import org.apache.spark.sql.types._

object XMLReader {
  def main(args: Array[String]): Unit = {

    Logger.getLogger( "org" ).setLevel( Level.ERROR )

    val spark = SparkSession.builder().
      master( "local" ).appName( "sample" ).
      getOrCreate()

    import spark.implicits._

    val df1 = spark.read.option( "rowTag", "book" ).format( "xml" ).
      load( "src\\main\\resources\\data\\dummy.xml" )

    df1.show

    val cust_schema = new StructType().
      add("_id", StringType, true).
      add("author", StringType, true).
      add("description", StringType, true).
      add("genre", StringType, true).
      add("price", DoubleType, true).
      add("publish_date", DateType, true).
      add("title", StringType, true)

    val df2 = spark.read.option("rowTag", "book").schema(cust_schema).
      format("xml").load("src\\main\\resources\\data\\dummy.xml")

    df2.show






  }

}
