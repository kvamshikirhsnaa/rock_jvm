package new_spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.types._

object Test8 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName( "sample21" ).
      master( "local" ).
      getOrCreate

    spark.sparkContext.setLogLevel( "ERROR" )

    spark.conf.set( "spark.sql.autoBroadcastJoinThreshold", -1 )

    spark.conf.set( "spark.sql.shuffle.partitions", 8 )

    import spark.implicits._

    // Pivot:

    val data = Seq(("Banana",1000,"USA"), ("Carrots",1500,"USA"), ("Beans",1600,"USA"),
      ("Orange",2000,"USA"),("Orange",2000,"USA"),("Banana",400,"China"),
      ("Carrots",1200,"China"),("Beans",1500,"China"),("Orange",4000,"China"),
      ("Banana",2000,"Canada"),("Carrots",2000,"Canada"),("Beans",2000,"Mexico"))

    val df = data.toDF("Product","Amount","Country")

    df.show()

    // it is expensive and slow

    val pivotDF1 = df.groupBy("Product").pivot("Country").sum("Amount")
    pivotDF1.show()


//    This will transpose the countries from DataFrame rows into columns and produces below output.
//    where ever data is not present, it represents as null by default.

//    Spark 2.0 on-wards performance has been improved on Pivot, however, if you are using lower
//      version. note that pivot is a very expensive operation hence, it is recommended to provide column
//      data (if known) as an argument to function as shown below.

    val countries = Seq("USA","China","Canada","Mexico")
    val pivotDF2 = df.groupBy("Product").pivot("Country", countries).sum("Amount")
    pivotDF2.show()

//    Another approach is to do two-phase aggregation. Spark 2.0 uses this implementation in order to
//    improve the performance

    val pivotDF3 = df.groupBy("Product","Country")
      .agg(sum("Amount") as "amt")
      .groupBy("Product")
      .pivot("Country")
      .sum("amt")

    pivotDF3.show()

//    Unpivot is a reverse operation, we can achieve by rotating column values into rows values.
//    Spark SQL doesn’t have unpivot function hence will use the stack() function.
//    Below code converts column countries to row.

    val unPivotDF = pivotDF3.select($"Product",
      expr("stack(4, 'Canada', Canada, 'China', China, 'Mexico', Mexico, 'USA', USA) as (Country, Total)"))
      .where("Total is not null")

    unPivotDF.show()

    df.groupBy("Product").pivot("Country", countries).agg(first("Amount")).show

    df.groupBy("Product").pivot("Country", countries).agg(collect_list("Amount")).show

    df.groupBy().pivot("Country", countries).agg(first("Amount")).show

    df.groupBy().pivot("Country", countries).agg(collect_list("Amount")).show

    df.groupBy().pivot("Country", countries).agg(sum("Amount")).show



  }

}
