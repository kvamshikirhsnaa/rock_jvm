package spark.newprac

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.functions.desc
import org.apache.spark.sql.types._

object Sample {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val df = spark.read.
      option("inferSchema", "true").
      option("header", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\aadhar.txt")

    //df.show

    println(df.rdd.getNumPartitions)

    val df2 = df.repartition(6, $"State")

    println(df2.count)

    df2.select(countDistinct($"State")).show

    val df3 = df2.withColumnRenamed("Enrolment Agency", "agency").
      withColumnRenamed("Sub District", "dist").
      withColumnRenamed("Pin Code", "pin").
      withColumnRenamed("Aadhaar generated", "generator").
      withColumnRenamed("Enrolment Rejected", "rejected").
      withColumnRenamed("Residents providing email", "email").
      withColumnRenamed("Residents providing mobile number", "number")

    df3.write.mode("Overwrite").
      save("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\aadharPar")


    val dfNew = spark.read.
      load("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\aadharPar").
      coalesce(6)

    val spec = Window.partitionBy($"State").orderBy($"dist")

    val dfNew2 = dfNew.withColumn("cnt", count($"dist")  over (spec))

    //dfNew2.show()

    println(dfNew2.count)

    println(dfNew2.explain())

    val dfNew3 = spark.read.
      option("inferSchema", "true").
      option("header", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\aadhar.txt").
      coalesce(6)

    val spec2 = Window.partitionBy($"State").orderBy($"Sub District")

    val df5 = dfNew3.withColumn("rnk", count($"Sub District") over (spec2))

    //df5.show()

    println(df5.count)

    println(df5.explain())


    val newDF = df.withColumnRenamed("Enrolment Agency", "agency").
      withColumnRenamed("Sub District", "dist").
      withColumnRenamed("Pin Code", "pin").
      withColumnRenamed("Aadhaar generated", "generator").
      withColumnRenamed("Enrolment Rejected", "rejected").
      withColumnRenamed("Residents providing email", "email").
      withColumnRenamed("Residents providing mobile number", "number")

    val newDF1 = newDF.write.mode("Overwrite").partitionBy("State").
      save("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\aadharPar2")



    val newDF2 = spark.read.
      option("inferSchema", "true").
      load("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\aadharPar2").
      coalesce(6)

    println(newDF2.count())

    val df6 = newDF2.withColumn("rnk", count($"dist") over (spec))

    println(df6.count)

    println(df6.explain())

















  }
}
