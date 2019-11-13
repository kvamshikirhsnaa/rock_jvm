package spark_poc

import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

import scala.util.Random

object CSVreader {
  def main(args: Array[String]): Unit = {

    val props = ConfigFactory.load()
    val envProps = props.getConfig(args(0))

    val spark = SparkSession.builder().
      master(envProps.getString("execution.mode")).
      appName("using mysql").getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")

   val df = spark.
      read.option("header", "true").
     option("inferSchema", "true").
     csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\cust.txt")

  // val df2 = spark.read.
    // load("C:\\Users\\Kenche.vamshikrishna\\Desktop\\Spark\\spark2\\part-00000-c3e13070-4934-4c21-8721-74a274b0fad6-c000.snappy.parquet")
    var x = 0

    def randgen(n: Int): Int = {
      try {
        if( x < n) {
          x = x + 1
        }
        x
      }
      catch { case _: NoSuchElementException => 0}
      finally {}
    }

    val randgent = udf(randgen _)

   val df1 = df.withColumn("aba", randgent(lit(5)) )

   df1.show

   // df.select(randgent(lit(5))).show

     //val a = explode(lit(Array( (Random.nextInt(9),(Random.nextInt(9) )))


    val df2 = spark.read.
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\sample.txt").
      toDF("id","name","sal")

    df2.show

    df2.createOrReplaceTempView("sample")

    val df3 = spark.sql("select concat_ws(',' , collect_list(name)) name,sal, count(sal) cnt from sample group by sal")

    df3.show

    val rdd1 = spark.sparkContext.
      textFile("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\sample.txt")

    //rdd1.collect.foreach(println)

    val rdd2 = rdd1.map(x => x.split(",")).map(x => (x(2).toInt,x(1)))

    rdd2.collect.foreach(println)

    val addOp = (name1: String) => name1
    val mergeOp = (name1: String, name2: String) => (name1 +" " +name2)
    val combOp = (p1: String, p2: String) => (p1 +" "+ p2)

    val rdd3 = rdd2.combineByKey(addOp, mergeOp, combOp)

    println("rdd3 result")

    rdd3.collect.foreach(println)


  }

}
