
package myprac.spark

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import frameless.functions.aggregate._
import frameless.TypedDataset
import frameless._
import frameless.syntax._

/*
case class Apartment(city: String, surface: Int, price: Double, bedrooms: Int)


object Test1 {
  def main(args: Array[String]): Unit = {

    implicit val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")



    val apartments = Seq(Apartment("Paris", 50, 300000.0, 2),
      Apartment("Paris", 100, 450000.0, 3),
      Apartment("Paris", 25, 250000.0, 1),
      Apartment("Lyon", 83, 200000.0, 2),
      Apartment("Lyon", 45, 133000.0, 1),
      Apartment("Nice", 74, 325000.0, 3))

//    val rdd = spark.sparkContext.parallelize(apartments)

   // using typeless frame creating dataset
    val aptTypedDs = TypedDataset.create(apartments)

   // using spark creating dataset
    val aptDs = spark.createDataset(apartments)

    aptDs.show

   // converting spark dataset to typed dataset
    val aptTypedDs2 = TypedDataset.create(aptDs)

    aptTypedDs2.show().run()

   // val aptTypedDs2 = aptTypedDs.typed

    val cities = aptTypedDs.select(aptTypedDs('city))

    cities.show().run()

   // aptTypedDs2.select(aptTypedDs2('citi)) // throws exceptions at compile time

   // aptDs.select('citi)  // throws exceptions at run time



   // aptTypedDs.select(aptTypedDs('surface).cast[Int]*10, aptTypedDs('surface).cast[Int] + 2).show().run()

   // val priceBySurfaceUnit = aptTypedDs.select(aptTypedDs('price) / aptTypedDs('surface).cast[Double])



  }



}
*/
