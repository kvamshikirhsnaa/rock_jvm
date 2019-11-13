package new_spark

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

case class SubRecord(x: Int)
case class ArrayElement(foo: String, bar: Int, vals: Array[Double])
case class Record(
                   an_array: Array[Int],
                   a_map: Map[String, String],
                   a_struct: SubRecord,
                   an_array_of_structs: Array[ArrayElement]
                 )

object Test12 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val df = spark.sparkContext.parallelize(Seq(
      Record( Array(1, 2, 3), Map("foo" -> "bar"), SubRecord(1),
        Array( ArrayElement("foo", 1, Array(1.0, 2.0, 2.0)),
          ArrayElement("bar", 2, Array(3.0, 4.0, 5.0))) ),
      Record( Array(4, 5, 6), Map("foz" -> "baz"), SubRecord(2),
        Array(ArrayElement("foz", 3, Array(5.0, 6.0)),
          ArrayElement("baz", 4, Array(7.0, 8.0))))
    )).toDF

    df.show

    df.printSchema

    //getting element from array
    df.select('an_array.getItem(1)).show
    df.select('an_array(0)).show

    //getting keys from map

    // using udf
    //val keys = udf[Seq[String], Map[String, String]](_.keys.toSeq)

                           //(OR)

    val keys = udf((x: Map[String,String]) => x.keys.toSeq)

    df.select(keys('a_map) as "keys").show


    // using built-in functions getting keys of map
    df.select(map_keys('a_map) as "keys").show


    //getting keys from map

    // using udf
   // val values = udf[Seq[String], Map[String, String]](_.values.toSeq)

                              //(OR)

    val values = udf((x: Map[String,String]) => x.values.toSeq)

    df.select(values('a_map) as "keys").show


    // using built-in functions getting values of map
    df.select(map_values('a_map) as "values").show


    //explode:

    // explode will split keys in one column and values in another column
    df.select(explode('a_map)).show

    // renaming columns for explode
    df.select(explode('a_map) as Seq("fname", "lname")).show




  }

}
