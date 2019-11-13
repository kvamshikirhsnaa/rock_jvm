package spark.newprac

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._


object Test1 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._


    val udfExampleDF = spark.range(5).toDF("num")

    udfExampleDF.show

    def power3(number: Double): Double = { number * number * number }

    println(power3(2.0))

    val power3udf = udf(power3(_:Double):Double)

    udfExampleDF.select(power3udf(col("num"))).show()


    val arr = Seq.range(0,100)

    val rdd = spark.sparkContext.parallelize(arr.map(x => Row(x)))

    val schema = StructType(Array(StructField("num", IntegerType, true)))

    val df = spark.createDataFrame(rdd, schema)

    df.show

    val myRow = Row("Hello", null, 1, false)

    println(myRow(0).getClass)
    //println(myRow(1).getClass) // throws null pointer exception
    println(myRow(2).getClass)
    println(myRow(3).getClass)

    val myDF = Seq(("saikrishna", 2, 100000L)).toDF("name", "id", "sal")

    val myDF2 = Seq(("saikrishna", 2, null)).toDF("name", "id", "sal")

    myDF.show
    myDF2.show


    // selecting column in different ways
    df.select(df.col("num"), col("num"), column("num"),
      'num, $"num", expr("num")).show

   // One common error is attempting to mix Column objects and strings.
    // For example, the following code will result in a compiler error:

    // df.select(df.col("num"), "num").show


    df.select(expr("num as number")).show(5)

    df.select(expr("num as number").alias("num")).show(5)

    df.selectExpr("num as number", "num").show(5)

    df.selectExpr("*", "num%2 = 0 as evens").show(5)

    df.withColumn("odds", expr("num%2 != 0")).show(5)

    df.withColumnRenamed("num", "number").show(5)

    df.withColumn("number", col("num").cast("long")).show(5)

    df.filter(col("num") > 50).show(5)
    df.where(col("num") > 50).show(5)

    df.filter(col("num") > 10 && $"num"%2 === 0).show(5)
    df.where(col("num") > 10 && $"num"%2 === 0).show(5)

    println(df.select("num").count)
    println(df.select("num").distinct().count)

    df.select($"num".cast("double")).show(5)
    df.select($"num".cast("Double")).show(5)

  }

}
