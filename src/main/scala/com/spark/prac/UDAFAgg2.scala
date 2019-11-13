package com.spark.prac

import org.apache.spark.sql.{Column, Encoder, Encoders, Row, SparkSession}
import org.apache.spark.sql.expressions.Aggregator
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.types._

class BelowThreshold[I](f: I => Boolean) extends Aggregator[I, Boolean, Boolean] with Serializable {
  def zero = false
  def reduce(acc: Boolean, x: I) = acc || f(x)
  def merge(acc1: Boolean, acc2: Boolean) = acc1 || acc2
  def finish(acc: Boolean) = acc

  def bufferEncoder: Encoder[Boolean] = Encoders.scalaBoolean
  def outputEncoder: Encoder[Boolean] = Encoders.scalaBoolean

}

object UDAFAgg2 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample22").
      master("local").
      getOrCreate

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")

    val belowThreshold = new BelowThreshold[(String, Int)](_._2 < -40).toColumn

    val df = spark.sparkContext.parallelize(Seq(("a", 0), ("a", 1), ("a", 50), ("b", -50),("b",40)))
      .toDF("name", "power")

    df.show

    df.as[(String,Int)].groupByKey(_._1).agg(belowThreshold).show

    val rdd = df.rdd.map(x => x.toString().replace("[","").replace("]",""))

    val rdd2 = rdd.map(x => (x.split(",")(0), x.split(",")(1).toInt))

    val zero = false

    def reduce(acc: Boolean, x: Int) = acc | x < -40

    def merger(acc1: Boolean, acc2: Boolean) = acc1 | acc2

    val rdd3 = rdd2.aggregateByKey(zero)(reduce, merger)

    rdd3.collect.foreach(println)

    df.createOrReplaceTempView("sample")

    spark.sql("select name, case when pow <= -40 then true else false end as power from (select name, min(power) pow from sample group by name)").show


    val rdd4 = spark.sparkContext.parallelize(Seq(("a",1),("a",2),("b",3),("a",4),("c",5),("c",6)))

    def zero1 = 0
    def reduce1(acc: Int, x: Int) = acc + x
    def merger1(acc1: Int, acc2: Int) = acc1 + acc2

    val rdd5 = rdd4.aggregateByKey(zero1)(reduce1, merger1)

    rdd5.collect.foreach(println)


    val newDF = spark.sparkContext.parallelize(Seq(("a", 0), ("a", 1), ("a", 50), ("b", -50),("b",40)))
      .toDF("name", "power")

    val finalDF = Seq(("empty",false)).toDF("name","power")

    val columnsForAggregation = newDF.columns.tail

//    def findTrue(cols: Column*) = cols.foldLeft(false) {
//      (temp, current) => when(current <= -40, true) otherwise temp
//    }

    val transformedDF = columnsForAggregation.foldLeft(finalDF){
      (tempDF, current) => {
        val aggregateDF = newDF.groupBy("name").agg(min(col(current)) as "min").
          select($"name",when($"min" <= -40, true) otherwise false as "power")

        finalDF.union(aggregateDF)
      }
    }

    transformedDF.filter($"name" =!= "empty").show

    }

}
