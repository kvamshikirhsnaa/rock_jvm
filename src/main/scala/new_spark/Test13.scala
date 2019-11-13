package new_spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import scala.util.Try


object Test13 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val df = Seq("1,saikrsihna", "2,aravind", "3,prakash", "4", "narahari").
      toDF("names")

    df.show

    val df2 = df.select(split('names, ",") as "newnames").
      drop("names")

    df2.show


    // creating UDF to get id or name based on type returns boolean
    val get_rec = udf((x: String) => Try(x.toInt).isSuccess)

    val df3 = df2.select((when(size('newnames) === 2, 'newnames(0))
    when(size('newnames) === 1 && get_rec('newnames(0)), 'newnames(0))
      otherwise null).cast(IntegerType) as "id",
    when(size('newnames) === 2, 'newnames(1))
    when(size('newnames) === 1 && !get_rec('newnames(0)), 'newnames(0))
      otherwise null as "name")

    df3.show

    df3.printSchema()



  }

}
