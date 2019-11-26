package spark_palyground_new

import org.apache.log4j.{Level, Logger}
import java.net.URL
import java.util.Properties

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}


object KafkaProducer {
  def main(args: Array[String]): Unit = {
    Logger.getLogger( "org" ).setLevel( Level.ERROR )

    val kafka_topic_name = "userdetails"
    val kafka_bootstrap_servers = "localhost:9092"








  }

}
