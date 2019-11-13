package spark_playground

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._

object Test3 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master("local").
      appName("sample").getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val df = spark.read.option("header", "true").
      option("inferSchema", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\emp1.txt")

    df.show
    df.printSchema()
/*
    df.repartition(1).write.
      save("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\emp_parq")
    */


    val empDF = spark.read.
      load("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\emp_parq")

    empDF.show

    val oldrecDF = empDF.join(df, Seq("id"), "leftouter").where(df("name").isNull).
      select(empDF("*"))

    oldrecDF.show

    val newrecDF = df.join(empDF, Seq("id"), "leftouter").
      select(df("*"))

    newrecDF.show

    val mixedDF = oldrecDF.union(newrecDF)

    mixedDF.show

/*

   // we can't write files by reading from parquet directory and overwriting into same directory

    mixedDF.write.
      mode("overwrite").format("parquet").
      save("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\delta\\emp_parq2")
*/

  }

}
