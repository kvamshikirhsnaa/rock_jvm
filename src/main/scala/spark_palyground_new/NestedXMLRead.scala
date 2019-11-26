package spark_palyground_new

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import com.databricks.spark.xml._
import org.apache.spark.sql.functions.{col, explode}
import org.apache.spark.sql.types._


object NestedXMLRead {
  def main(args: Array[String]): Unit = {

    Logger.getLogger( "org" ).setLevel( Level.ERROR )

    val spark = SparkSession.builder().
      master( "local" ).appName( "sample" ).
      getOrCreate()

    import spark.implicits._

    val df1 = spark.read.option( "rowTag", "CourseOffering" ).
      option("inferSchema", "true").format( "xml" ).
      load( "C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\dummy2.xml" )

    df1.show(false)
    df1.printSchema()

    val cust_schema = new StructType().
      add("Id", IntegerType, true).
      add("Semistar", StringType, true).
      add("Enrolled", IntegerType, true).
      add("Professor", new StructType().
        add("Id", IntegerType, true).
        add("Name", StringType, true).
        add("School", StringType, true), true).
      add("Course", new StructType().
        add("Id", IntegerType, true).
        add("Number", IntegerType, true).
        add("CourseName", StringType, true).
        add("Department", StringType, true).
        add("MaximumStudents", IntegerType, true), true)

    val df2 = spark.read.option( "rowTag", "CourseOffering" ).
      schema(cust_schema).format( "xml" ).
      load( "src\\main\\resources\\data\\dummy2.xml" )

    df2.show(false)
    df2.printSchema()



    var df3 = df2

    var nested_column_count = 1

    while (nested_column_count != 0) {

      if (df3.schema.map( x => x.dataType ).map( x => x.typeName ).contains( "array" )) {
        val arr = df3.schema.map( x => (x.name, x.dataType) ).filter( x => x._2.typeName == "array" ).map( x => x._1 )
        df3 = arrayDF( df3, arr )
      }

      if (df3.schema.map( x => x.dataType ).map( x => x.typeName ).contains( "struct" )) {
        val str = df3.schema.map( x => (x.name, x.dataType) ).filter( x => x._2.typeName == "struct" ).map( x => x._1 )
        df3 = structDF( df3, str )
      }

      if (df3.schema.map(x => x.dataType).map(x => x.typeName).forall(x => (x != "struct") && (x != "array"))) {
        nested_column_count = 0
      }
    }

    df3.show
    df3.printSchema()

  }

  def structDF(df: DataFrame, str: Seq[String]): DataFrame = str.foldLeft(df){
    (tempDF, curr) => {
      val nestCols = tempDF.schema(curr).dataType.asInstanceOf[StructType].fields.map(x => x.name)
      val dfnew = nestCols.foldLeft(tempDF) {
        (tdf, fld) => {
          tdf.withColumn(s"${curr}_${fld}", col(s"${curr}.${fld}"))
        }
      }
      dfnew.drop(curr)
    }
  }

  def arrayDF(df: DataFrame, arr: Seq[String]): DataFrame = arr.foldLeft(df){
    (tempDF, curr) => {
      tempDF.withColumn(curr, explode(col(curr)))
    }
  }





}
