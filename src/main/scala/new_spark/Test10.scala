package new_spark

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

object Test10 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val df = spark.read.option("inferSchema", "true").
      option("header", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\dummy5.txt")

    df.show

    // transposing(pivoting) without aggregating

    // Input:
//    +-----------+-------+
//    |name       | value |
//    +-----------+-------+
//    |col1       | val1  |
//    |col2       | val2  |
//    |col3       | val3  |
//    |col4       | val4  |
//    |col5       | val5  |
//    +-----------+-------+

//    Expected Output:
//      +-----+-------+-----+------+-----+
//    |col1 | col2  |col3 | col4 |col5 |
//    +-----+-------+-----+------+-----+
//    |val1 | val2  |val3 | val4 |val5 |
//    +-----+-------+-----+------+-----+

//    solution:

//    If your dataframe is small enough as in the question, then you can collect
//      "name" to form schema and collect "value" to form the rows and then create a new dataframe as


    //creating schema from existing dataframe
    val schema = StructType(df.select(collect_list("name")).head.
      getAs[Seq[String]](0).map(x => StructField(x, StringType)))

    //creating RDD[Row]
    val values = spark.sparkContext.parallelize(Seq(Row.fromSeq(df.select(collect_list("VALUE")).
      first().getAs[Seq[String]](0))))


    //new dataframe creation
    val df2 = spark.createDataFrame(values, schema)

    df2.show


    // another approach

    val df3 = df.groupBy().pivot("name").agg(first("value"))

    df3.show


    // another approach

//    if your data frame is really that small as in your example, you can collect it as Map:
//    collecting input df as Map and converting as required output data frame

    val map = df.as[(String,String)].collect().toMap

    val df4 = map.tail.foldLeft( Seq(map.head._2).toDF(map.head._1) ){
      (acc,curr) => acc.withColumn(curr._1,lit(curr._2))
    }

    df4.show



  }

}
