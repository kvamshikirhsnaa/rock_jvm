package com.spark.prac

import org.apache.spark.sql.{Row,SparkSession}
import org.apache.spark.sql.expressions.MutableAggregationBuffer
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._



//  User-Defined Aggregation Functions:
//
//  User-Defined Aggregation Functions (UDAFs) are a way for users to define
//  their own aggregation functions based on custom formulae or business rules.
//  You can use UDAFs to compute custom calculations over groups of input data
//  (as opposed to single rows).
//
//  Spark maintains a single `AggregationBuffer` to store intermediate results for
//  every group of input data.
//
//  To create a UDAF, you must inherit from the base class `UserDefinedAggregateFunction`
//  and implement the following methods:
//    • inputSchema represents input arguments as a StructType
//    • bufferSchema represents intermediate UDAF results as a StructType
//    • dataType represents the return DataType
//    • deterministic is a Boolean value that specifies whether this UDAF will return the
//      same result for a given input
//    • initialize allows you to initialize values of an aggregation buffer
//    • update describes how you should update the internal buffer based on a given row
//    • merge describes how two aggregation buffers should be merged
//    • evaluate will generate the final result of the aggregation
//
//  The method call is quite similar, but instead of calling rollup, we call cube:
//  The following example implements a `BoolAnd`, which will inform us whether all the
//  rows (for a given column) are true; if they’re not, it will return false.


class BoolAnd extends UserDefinedAggregateFunction {
  def inputSchema: StructType = StructType(StructField("value",BooleanType) :: Nil)
  def bufferSchema: StructType = StructType(StructField("result", BooleanType) :: Nil)
  def dataType: DataType = BooleanType
  def deterministic: Boolean = true
  def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = true
  }
  def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    buffer(0) = buffer.getAs[Boolean](0) && input.getAs[Boolean](0)
  }
  def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0) = buffer1.getAs[Boolean](0) && buffer2.getAs[Boolean](0)
  }
  def evaluate(buffer: Row): Any = buffer(0)
}


object UDFAgg {

  val spark = SparkSession.builder().
    appName("sample21").
    master("local").
    getOrCreate

  import spark.implicits._

  spark.sparkContext.setLogLevel("ERROR")

  def main(args: Array[String]): Unit = {

    val ba = new BoolAnd

    spark.udf.register("booland", ba)

    spark.range(1).selectExpr("explode(array(TRUE,TRUE,TRUE)) as t")
      .selectExpr("t", "explode(array(TRUE,FALSE,TRUE)) as f").show

    spark.range(1).selectExpr("explode(array(TRUE,TRUE,TRUE)) as t")
      .selectExpr( "t", "explode(array(TRUE,FALSE,TRUE)) as f")
      .select(ba(col("t")), expr("booland(f)")).show


  }
}