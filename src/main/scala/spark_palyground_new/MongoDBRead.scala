package spark_palyground_new

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._


object MongoDBRead {
  def main(args: Array[String]): Unit = {

    Logger.getLogger( "org" ).setLevel( Level.ERROR )

    val spark = SparkSession.builder().
      master( "local" ).appName( "sample" ).
      getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val mongodb_host_name = "localhost"
    val mongodb_port_no = "27017"
   // val mongodb_user_name = "admin"
   // val mongodb_password = "admin"
    val mongodb_datebase_name = "test_db"
    val mogodb_collection_name = "user_details"

/*
    // if we have user name and password authentication for mongodb, need to use below uri
    val mongodb_uri = s"mongodb://${mongodb_user_name}:${mongodb_password}@${mongodb_host_name}:" +
      s"${mongodb_port_no}/${mongodb_datebase_name}.${mogodb_collection_name}"

*/
    val mongodb_uri = s"mongodb://${mongodb_host_name}:" +
      s"${mongodb_port_no}/${mongodb_datebase_name}.${mogodb_collection_name}"

    println("mongodb_ uri" + mongodb_uri)

    val df = spark.read.format("mongo").
      option("uri", mongodb_uri).
      option("database", mongodb_datebase_name).
      option("collection", mogodb_collection_name).
      load()

    df.show
    df.printSchema()




  }

}
