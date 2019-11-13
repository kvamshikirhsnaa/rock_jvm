package com.spark.prac

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object FoldLeftAgg6 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val df = spark.sparkContext.parallelize(Seq(("A1", "B1", "C1", "D1", "E1", "F1",1),
      ("A2", "B2", "C2", "D2", "E2", "F2",2),("A1", "B1", "C1", "D1", "E1", "F1",1),
      ("A2", "B2", "C2", "D2", "E2", "F2",2))).toDF("A","B","C","D","E","F","amt")

    df.show

    // selecting the columns without A and amt
    val columnsForAggregation = df.columns.tail.toSet - "amt"

    println(columnsForAggregation.foreach(println))



    val grpdf = Seq(("empty", "empty", 0)).toDF("A","value", "amt")


    val dfnew = columnsForAggregation.foldLeft(grpdf) {
      // aggregation on the dataframe with A and one of the column and finally selecting as required in the output
      (tempDF, column) => {
        val aggregateDF = df.groupBy( "A", column ).agg( sum( "amt" ) as "amt" )
        tempDF.union( aggregateDF )
      }

    }
    dfnew.show


        // creating an empty dataframe (format for final output)
   val finalDF = Seq(("empty","empty","empty",0.0)).toDF("A", "field", "value", "amt")

   finalDF.show


    // using foldLeft for the aggregation and merging each aggreted results

    val transformeddf1 = columnsForAggregation.foldLeft(finalDF) {
      // aggregation on the dataframe with A and one of the column and finally selecting as required in the output
      (tempDF, column) =>  {
        val aggregateDF = df.groupBy("A", column).agg(sum("amt") as "amt")
          .select(col("A"),lit(column) as "field", col(column) as "value", col("amt"))

        tempDF.union(aggregateDF)
      }
    }

    transformeddf1.filter($"A" =!= "empty").show



    // can do like below also
    //    val (originaldf, transformeddf) = columnsForAggregation.foldLeft((df, finalDF)) {
    //      //aggregation on the dataframe with A and one of the column and finally selecting as required in the output
    //      (tempDF, column) =>  {
    //        val aggregateDF = tempDF._1.groupBy("A", column).agg(sum("amt") as "amt")
    //          .select(col("A"),lit(column) as "field", col(column) as "value", col("amt"))
    //
    //        (df, tempDF._2.union(aggregateDF))
    //      }
    //    }

    //    val resDF = transformeddf.filter($"A" =!= "empty")
    //
    //    resDF.show









  }

}
