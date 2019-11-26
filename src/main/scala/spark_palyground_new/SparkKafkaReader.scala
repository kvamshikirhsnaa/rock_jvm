package spark_palyground_new


import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

object SparkKafkaReader {
  def main(args: Array[String]): Unit = {
    Logger.getLogger( "org" ).setLevel( Level.ERROR )

    val spark = SparkSession.builder().
      master( "local" ).appName( "sample" ).
      getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val kafka_topic_name = "userdetails"
    val kafka_bootstrap_servers = "localhost:9092"

    val df = spark.read.format("kafka").
      option("kafka.bootstrap.servers", kafka_bootstrap_servers).
      option("subscribe", kafka_topic_name).
      load()

    val df2 = df.select('value.cast(StringType), 'timestamp.cast(TimestampType))

    val cust_schema = new StructType().
      add("user_id", IntegerType, true).
      add("user_name", StringType, true).
      add("user_city", StringType, true)

    val df3 = df2.select(from_json('value, cust_schema) as "user_details", 'timestamp)

    val df4 = df3.select($"user_details.*", 'timestamp)

    df4.show
    df4.printSchema()



  }

}
