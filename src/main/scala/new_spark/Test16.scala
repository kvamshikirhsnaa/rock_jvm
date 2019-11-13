package new_spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._


case class StudentNew(name: String, maths: Double, physics: Double, chemistry: Double, english: Double)

object Test16 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName( "sample21" ).
      master( "local" ).
      getOrCreate

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._

    val s1 = StudentNew("Saikrishna", 100, 95, 90, 90)
    val s2 = StudentNew("Aravind", 90, 90, 85, 85)
    val s3 = StudentNew("Prakash", 85, 80, 75, 70)
    val s4 = StudentNew("Narahari", 80, 75, 70, 70)
    val s5 = StudentNew("Vamshikrishna", 80, 70, 65, 70)

    val df = spark.sparkContext.parallelize(Seq(s1,s2,s3,s4,s5)).toDF
    df.show

    // creating an empty dataframe (format for final output)
    val finalDF = Seq(("empty","empty", 0.0)).toDF("name", "subject", "marks")

    val columnsOfMarks = df.columns.tail
    columnsOfMarks.foreach(println)

    val transformeddf1 = columnsOfMarks.foldLeft(finalDF) {
      (tempDF, column) =>  {
        val unpivotDF = df.select(col("name"),lit(column) as "subject", col(column) as "marks")

        tempDF.union(unpivotDF)
      }
    }

    transformeddf1.show

    val resdf = transformeddf1.filter('name =!= "empty").sort('name)

    resdf.show

  }

}
