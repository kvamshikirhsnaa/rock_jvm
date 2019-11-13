package spark_playground

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._

object Test11 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master( "local" ).
      appName( "sample" ).getOrCreate()

    spark.sparkContext.setLogLevel( "ERROR" )

    import spark.implicits._

    val df1 = spark.read.option( "header", "true" ).
      option( "inferSchema", "true" ).
      csv( "C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\sample4.txt")

    df1.show
    df1.printSchema()

    val df2 = df1.withColumn("id", when('dno === 10, 1) when('dno === 11, 2)
      when('dno === 12, 3))

    df2.show

    val spec = Window.partitionBy("id").orderBy("id")

    val df3 = df2.withColumn("dno2", lag('dno, 1) over spec)
    //sort('loc)

    df3.show

    val lst = df3.select('loc).collect().map(x => x.toString.replace("[", "").
      replace("]", ""))

    lst.foreach(x => print(x + " "))
    println()

    val lst2 = lst.distinct

    val df4 = df1.groupBy("dno").pivot("loc", lst2).agg(first("name"))
    df4.show

    val df5 = df2.groupBy("dno").pivot("loc", lst2).
      agg(first("name") as "name", first("id") as "id")
    df5.show
  }

}
