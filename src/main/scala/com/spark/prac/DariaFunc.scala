package com.spark.prac

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._
import com.github.mrpowers.spark.daria.sql.functions._
import com.github.mrpowers.spark.daria.sql.DataFrameHelpers
import com.github.mrpowers.spark.daria.sql.DataFrameHelpers._

object DariaFunc {
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

//    spark-daria has some custom built in functions developed by some other party people

//    removeAllWhitespace:

    val actualDF = sourceDF
      .columns
      .foldLeft(sourceDF) { (memoDF, colName) =>
        memoDF.withColumn(
          colName,
          removeAllWhitespace(col(colName))
        )
      }

    actualDF.show

    sourceDF.select(removeAllWhitespace($"name") as "name", removeAllWhitespace($"country") as "country",
      removeAllWhitespace($"profession") as "profession").show

//    DataFrameHelpers:

//    The DataFrame helper methods make it easy to convert DataFrame columns into
//      Arrays or Maps. Here's how to convert a column to an Array.

    val arr = DataFrameHelpers.columnToArray[String](sourceDF, "name") // res is not dataframe

//    validatePresenceOfColumns:
//
//    DataFrame validators check that DataFrames contain certain columns or a
//    specific schema. They throw descriptive error messages if the DataFrame
//    schema is not as expected. DataFrame validators are a great way to make sure
//    your application gives descriptive error messages.
//
//    Let's look at a method that makes sure a DataFrame contains the expected columns.

    val sourceDF2 = Seq(
      ("jets", "football"),
      ("nacional", "soccer")
    ).toDF("team", "sport")

    val requiredColNames = Seq("team", "sport", "country", "city")

    //validatePresenceOfColumns(sourceDF2, requiredColNames) // it will throw error

//    The spark-daria snakeCaseColumns() custom transformation snake_cases all
//    of the column names in a DataFrame.
//
//    import com.github.mrpowers.spark.daria.sql.transformations._
//
//    val betterDF = df.transform(snakeCaseColumns())
//
//    Protip: You'll always want to deal with snake_case column names in Spark
//    use this function if your column names contain spaces of uppercase letters.

//    there are many other functions find at https://github.com/mrpowers/spark-daria


    def toSnakeCase(str: String): String = {
      str.toLowerCase().replace(" ", "_")
    }

    def snakeCaseColumns(df: DataFrame): DataFrame = {
      df.columns.foldLeft(df) { (memoDF, colName) =>
        memoDF.withColumnRenamed(colName, toSnakeCase(colName))
      }
    }

    val sourceDF3 = Seq(
      ("funny", "joke")
    ).toDF("A b C", "de F")

    val actualDF3 = sourceDF3.transform(snakeCaseColumns)

    actualDF3.show






  }

}
