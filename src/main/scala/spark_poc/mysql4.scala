package spark_poc


import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object mysql4 {
  def main(args: Array[String]): Unit = {


    val spark = SparkSession.builder().
      master("local").
      appName("using mysql4").getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")


//    val custSchema = StructType(Array(StructField("tname", StringType, true),
//      StructField("lastsink",StringType,true)))

    val df1 = spark.read.format("jdbc").
      option("url","jdbc:mysql://localhost:3306/kvk").
      option("dbtable","control5").
      option("user","root").
      option("password","Kenche@21").option("inferSchema", "true").load.
      toDF("tname", "lastsink")

    val rdd1 = df1.rdd.map(x => x.toString().replace("[", "").
      replace("]", ""))

    val rdd2 = rdd1.map(x => (x.split(",")(0), x.split(",")(1)))

    var pair3 = rdd2.collect.toMap



    println(pair3)
    println(pair3.get("table1"))
    println(pair3.get("table1").get == "null")

    val aadharDF1 = spark.read.format("jdbc").
      option("url", "jdbc:mysql://localhost:3306/kvk").
      option("dbtable", "employee").
      option("user", "root").
      option("password", "Kenche@21").
      option("inferSchema", "true").
      option("header", "true").load

    val aadharDF2 = spark.read.format("jdbc").
      option("url", "jdbc:mysql://localhost:3306/kvk").
      option("dbtable", "employee").
      option("user", "root").
      option("password", "Kenche@21").
      option("inferSchema", "true").
      option("header", "true").load.
      where($"sink" > pair3.get("table1").get)

    // aadharDF2.show

    if (pair3.get("table1").get == "null") {

      aadharDF1.show

      aadharDF1.write.format("jdbc").
        option("url","jdbc:mysql://localhost:3306/kvk").
        option("user","root").
        option("password","Kenche@21").
        option("dbtable", "empnew").
        mode("append").
        save()

      println(current_timestamp())
    }
    else {

      aadharDF2.write.format("jdbc").
        option("url","jdbc:mysql://localhost:3306/kvk").
        option("user","root").
        option("password","Kenche@21").
        option("dbtable", "empnew").
        mode("append").
        save()
    }

    val df2 = df1.select($"tname", when($"lastsink" isNull, current_timestamp)
         when($"lastsink" < current_timestamp, current_timestamp) as "lastsink")

    df2.write.format("jdbc").
      option("url","jdbc:mysql://localhost:3306/kvk").
      option("dbtable", "control5").
      option("user","root").
      option("password","Kenche@21").mode("overwrite").save()

    println(current_timestamp())

  }

}


