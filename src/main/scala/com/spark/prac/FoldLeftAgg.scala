package com.spark.prac

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._
import com.github.mrpowers.spark.daria.sql.functions._
import com.github.mrpowers.spark.daria.sql.DataFrameHelpers
import com.github.mrpowers.spark.daria.sql.DataFrameHelpers._



object FoldLeftAgg {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample").master("local").
      getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val sourceDF = Seq(
      ("  p a   b l o", "Paraguay","foo t ba ll pla y er"),
      ("Neymar", "B r    asil", "f oo t b al lp la yer")
    ).toDF("name", "country", "profession")

    sourceDF.show()

    val actualDF = Seq("name", "country", "profession").foldLeft(sourceDF) {
      (memoDF, colName) => memoDF.withColumn(
        colName, regexp_replace(col(colName), "\\s+", ""))
    }

    actualDF.show

//    sourceDF.columns.foldLeft(sourceDF) {
//      (memoDF, colName) => memoDF.withColumn(
//        colName, regexp_replace(col(colName), "\\s+", ""))
//    }
 //    can write like above also

    //actualDF.schema.fields.filter()


    // other example
    val data = Seq(("a",10),("b",20),("a",30),("b",40),("c",50))

    // adding elements group by using scala

    data.groupBy(x => x._1).mapValues(x => x.map(x => x._2).sum)

    // or using foldLeft

    data.foldLeft(Map[String, Int]()){
      case (m, (k,v)) => m + (k -> (v + m.getOrElse(k, 0)))
    }

  }
}