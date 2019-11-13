package new_spark

import org.apache.spark.sql.{SparkSession, DataFrame, Column}
import org.apache.spark.sql.functions._


object Test17 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName( "sample21" ).
      master( "local" ).
      getOrCreate

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._

    val df1 = List(("1","new","current"), ("2","closed","saving"), ("3","blocked","credit")).
      toDF("id","type","account")

    val df2 = List(("1","7"), ("2","5"), ("5","8")).toDF("id","value")

    val dfJoinResult = df1
      .join(df2, df1("id") === df2("id"), "inner")
      .select(df1("type"), df1("account"), df2("value"))

    dfJoinResult.show

    // creating function to join 2 dataframes
    val joinExpr = Seq("id")
    val selectExpr = Seq(df1("type"), df1("account"), df2("value"))


    val testDf = joinDF(df1, df2, joinExpr, "inner", selectExpr)

    testDf.show

  }

  // passing column names which are required in selectExpr
  def joinDF(df1: DataFrame,  df2: DataFrame , joinExpr: Seq[String], joinType: String, selectExpr: Seq[Column]): DataFrame = {
    val dfJoinResult = df1.join(df2, joinExpr, joinType)
    dfJoinResult.select(selectExpr:_*)

  }



/*  def joinDF(df1: DataFrame,  df2: DataFrame , joinExpr: Seq[String], joinType: String): DataFrame = {
    val dfJoinResult = df1.join(df2, joinExpr, joinType)
    dfJoinResult.select(df1("type"),df1("account"), df2("value"))
  }*/

}
