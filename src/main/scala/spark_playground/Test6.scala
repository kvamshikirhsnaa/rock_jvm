package spark_playground

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._
import io.delta.tables._

object Test6 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master( "local" ).
      appName( "sample" ).getOrCreate()

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._

    val df1 = spark.read.option( "header", "true" ).
      option( "inferSchema", "true" ).
      csv( "C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\data\\empl1.txt" )

    df1.show
    df1.printSchema()

/*

    df1.repartition(1).write.format("delta").
      save("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\data\\emp_new_merge")

*/

    val df2 = spark.read.option( "header", "true" ).
      option( "inferSchema", "true" ).
      csv( "C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\data\\empl2.txt" )


    df2.show
    df2.printSchema()
/*

    df2.repartition(1).write.format("delta").
      mode("overwrite").option("overwriteSchema", "true").
      save("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\data\\emp_new_merge")

*/

    val dt = DeltaTable.forPath("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\data\\emp_new_merge")

    dt.toDF.show
    dt.toDF.printSchema()
  }

}
