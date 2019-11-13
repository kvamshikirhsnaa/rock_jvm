package spark_poc

import java.sql.Timestamp
import java.text.SimpleDateFormat

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

import scala.collection.mutable.HashMap
import java.time.LocalDateTime

import scala.util.{Failure, Success, Try}

object aadhar2 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master("local").
      appName("aadhar data analysis").
      getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")
    spark.conf.set("spark.sql.shuffle.partitions", 2)

    val df1 = spark.read.
      option("inferSchema", "true").option("header", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\control.txt")

    df1.show
    df1.printSchema()

    // type casting
    // val df4 = df1.withColumn("eventTime", $"time".cast(TimestampType)).drop($"time")

    val df2 = df1.withColumn("eventTime", unix_timestamp($"time", "yyyy-MM-dd'T'HH:mm:ss.SSS")
       .cast(TimestampType)).drop($"time")

    // df2.printSchema()

    val data = df2.rdd.map(x => x.toString().replace("[", "").replace("]", ""))

    val data2 = data.map(x => (x.split(",")(0), x.split(",")(1)))

    data2.take(2).foreach(println)

    var pair2 = data2.collectAsMap()

    def getTimestamp(s: String) : Option[Timestamp] = s match {
      case "" => None
      case _ => {
        val format = new SimpleDateFormat("MM/dd/yyyy' 'HH:mm:ss")
        Try(new Timestamp(format.parse(s).getTime)) match {
          case Success(t) => Some(t)
          case Failure(_) => None
        }
      }
    }

    val pair3 = pair2.map(x => (x._1, getTimestamp(x._2)))

//    if (pair3.get("aadhar1") == None ) {
//      val df2 = spark.read.
//        option("inferSchema", "true").option("header", "true").
//        csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\aadhar.txt")
//    }
//    else {
//      val df2 = spark.read.
//        option("inferSchema", "true").option("header", "true").
//        csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\aadhar.txt").
//        where("update" > pair3.get("aadhar1") )
//    }

   // pair2 = pair2.updated("aadhar1",LocalDateTime.now())







    //pair += ("hfdyr" -> "rhrur")

    //val pair2 =  new HashMap[String,String]()

    //pair2 += pair.collect.toMap



    //val table1lastSync = get the entry from the hashMap
    //val df4 = df2.

//      if {
//        val df3 = spark.read.
//          option("inferSchema", "true").option("header", "true").
//          csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\aadhar2.txt")
//
//      }else
//        {
//
//        }
//    val table2lastSync = get the entry from the hashMap
//    val df4 = df2.
//
//
//    val df3 = spark.read.
//      option("inferSchema", "true").option("header", "true").
//      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\aadhar2.txt")
//      .where("update_at" > )
//
//    df3.show(10)

  }
}
